package com.gowil.zzleep.presenter

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.UserRegisterRaw
import com.gowil.zzleep.data.mapper.UsersDataMapper
import com.gowil.zzleep.data.store.ConstantsTypeServices
import com.gowil.zzleep.domain.interactor.UsersInteractor
import com.gowil.zzleep.domain.model.UserRegister
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack
import com.gowil.zzleep.domain.repository.interactor.UsersServiceRepository
import com.gowil.zzleep.repository.UsersDataRepository
import com.gowil.zzleep.repository.dataSource.user.UsersDataStoreFactory
import com.gowil.zzleep.view.UserView

class UsersPresenter : Presenter<UserView>, RequestCallBack{

    val TAG = "UsersPresenter: "
    var view: UserView? = null
    var interactor: UsersInteractor? = null
    var serviceRepository: UsersServiceRepository? = null

    fun userRegister(raw: UserRegisterRaw){
        LogUtils().v(TAG, raw.toString())

        view!!.showLoading()
        interactor!!.setUsers(raw, this)
    }


    override fun attachedView(view: UserView) {
        this.view = view
        serviceRepository = UsersDataRepository(UsersDataStoreFactory(this.view!!.getContext()), UsersDataMapper())
        interactor = UsersInteractor(serviceRepository!!)
    }

    override fun removeView() {
        view = null
    }

    override fun onRequestSuccess(any: Any) {

    }

    override fun onRequestSuccess(any: Any, type: Int) {
        when(type){
            ConstantsTypeServices().USER_CREATE -> {
                val userCreate = any as UserRegister
                view!!.usersRegister(userCreate)
            }
        }

    }

    override fun onRequestFailure(e: Throwable) {

    }

    override fun onRequestFailure(throwable: Throwable, type: Int) {
        when(type){
            ConstantsTypeServices().USER_CREATE -> {
                view!!.showMessageError(throwable.message!!, type)
            }
        }
    }
}