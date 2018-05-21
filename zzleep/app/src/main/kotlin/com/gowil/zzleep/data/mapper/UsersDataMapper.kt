package com.gowil.zzleep.data.mapper

import com.gowil.zzleep.data.entity.response.UserRegisterResponse
import com.gowil.zzleep.domain.model.UserRegister

class UsersDataMapper {

    fun UsersRegisterMapper(response: UserRegisterResponse): UserRegister {
        var userRegister = UserRegister()
        userRegister!!.display_name = response.data!!.display_name
        userRegister!!.email = response.data!!.email
        userRegister!!.photo_url = response.data!!.photo_url
        userRegister!!.uid = response.data!!.uid
        return userRegister
    }
}