package com.gowil.zzleep.repository

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.UserRegisterRaw
import com.gowil.zzleep.data.entity.response.UserRegisterResponse
import com.gowil.zzleep.data.mapper.UsersDataMapper
import com.gowil.zzleep.data.store.ConstantsTypeServices
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack
import com.gowil.zzleep.domain.repository.interactor.UsersServiceRepository

import com.gowil.zzleep.repository.dataSource.user.UsersDataStoreFactory

class UsersDataRepository(val dataStoreFactory: UsersDataStoreFactory, val mapper: UsersDataMapper): UsersServiceRepository {
    val TAG = "UsersDataRepository"

    override fun usersRegisterRequest(raw: UserRegisterRaw, requestCallBack: RequestCallBack) {
        val serviceRepository = this.dataStoreFactory.create(UsersDataStoreFactory.CLOUD)
        serviceRepository.setUsers(raw, object : RepositoryCallBack {
            override fun onSuccess(any: Any) {
                val userRegister = mapper.UsersRegisterMapper(any as UserRegisterResponse)
                LogUtils().v(TAG, userRegister.toString())
                requestCallBack.onRequestSuccess(userRegister, ConstantsTypeServices().USER_CREATE)
            }

            override fun onSuccess(any: Any, header: Any) {

            }

            override fun onFailure(throwable: Throwable) {
                requestCallBack.onRequestFailure(throwable, ConstantsTypeServices().USER_CREATE)

            }
        })
    }

}