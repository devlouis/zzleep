package com.gowil.zzleep.view

import com.gowil.zzleep.domain.model.UserRegister

interface UserView: BaseView {
    fun usersRegister(userRegister: UserRegister)
    fun showLoading()
    fun hideLoading()

}