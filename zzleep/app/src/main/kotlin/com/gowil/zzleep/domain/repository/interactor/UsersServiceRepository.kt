package com.gowil.zzleep.domain.repository.interactor

import com.gowil.zzleep.data.entity.raw.UserRegisterRaw

interface UsersServiceRepository {
    fun usersRegisterRequest(raw: UserRegisterRaw, requestCallBack: RequestCallBack)
}