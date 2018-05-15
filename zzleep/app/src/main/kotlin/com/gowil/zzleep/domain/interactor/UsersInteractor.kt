package com.gowil.zzleep.domain.interactor

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.UserRegisterRaw
import com.gowil.zzleep.domain.model.UserRegister
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack
import com.gowil.zzleep.domain.repository.interactor.UsersServiceRepository

class UsersInteractor(val serviceRepository: UsersServiceRepository) {
    companion object {
        private val TAG = "UsersInteractor"
    }

    fun setUsers(raw: UserRegisterRaw, requestCallBack: RequestCallBack){
        serviceRepository.usersRegisterRequest(raw, object : RequestCallBack {
            override fun onRequestSuccess(any: Any) {

            }

            override fun onRequestSuccess(any: Any, type: Int) {
                val userRegister = any as UserRegister
                requestCallBack.onRequestSuccess(userRegister, type)

            }

            override fun onRequestFailure(e: Throwable) {

            }

            override fun onRequestFailure(throwable: Throwable, type: Int) {
                LogUtils().v(TAG, " setUsers-onRequestFailure " + throwable.toString())
                requestCallBack.onRequestSuccess(throwable, type)
            }
        })
    }
}