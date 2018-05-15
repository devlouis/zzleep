package com.gowil.zzleep.data.mapper

import com.gowil.zzleep.data.entity.response.UserRegisterResponse
import com.gowil.zzleep.domain.model.UserRegister

class UsersDataMapper {

    fun UsersRegisterMapper(response: UserRegisterResponse): UserRegister {
        var userRegister: UserRegister? = null
        userRegister!!.display_name = response.data!!.display_name
        userRegister!!.email = response.data!!.display_name
        userRegister!!.photo_url = response.data!!.display_name
        userRegister!!.uid = response.data!!.display_name
        return userRegister
    }
}