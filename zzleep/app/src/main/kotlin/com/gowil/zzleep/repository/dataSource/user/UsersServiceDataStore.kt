package com.gowil.zzleep.repository.dataSource.user

import com.gowil.zzleep.data.entity.raw.UserRegisterRaw
import com.gowil.zzleep.repository.RepositoryCallBack

interface UsersServiceDataStore {
    fun setUsers(raw: UserRegisterRaw, repositoryCallBack: RepositoryCallBack)
}