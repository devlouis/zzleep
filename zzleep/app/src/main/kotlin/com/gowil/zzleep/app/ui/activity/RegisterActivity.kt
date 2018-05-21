package com.gowil.zzleep.app.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import com.gowil.zzleep.R
import com.gowil.zzleep.app.core.BaseAppCompat
import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.app.core.utils.ValidateEmail
import com.gowil.zzleep.data.entity.raw.UserRegisterRaw
import com.gowil.zzleep.domain.model.UserRegister
import com.gowil.zzleep.presenter.UsersPresenter
import com.gowil.zzleep.view.UserView
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity: BaseAppCompat(), UserView {
    val TAG = " RegisterActivity "
    var usersPresenter = UsersPresenter()
    var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getBundle()
        onClickListener()
    }

    fun getBundle(){
        if (intent.extras != null){
            phone = intent.extras.getString("PHONE")
        }
        initUI()
    }

    fun initUI(){
        usersPresenter.attachedView(this)
    }

    fun validateForm(){
        if (regName.text.toString().isEmpty()){
            snackBarFail("Ingrese su nombre", regName)
        } else if (regLastName.text.isEmpty()){
            snackBarFail("Ingrese su Apellido", regLastName)
        } else if (regEmail.text.toString().isEmpty()){
            regEmail.requestFocus()
            snackBarFail("Ingrese su correo", regLastName)
        } else if (!ValidateEmail().validateEmail(regEmail.text.toString().trim())){
            regEmail.requestFocus()
            snackBarFail("Formato de correo inválido", regLastName)
        } else if (regPassword.text.toString().isEmpty()){
            regEmail.requestFocus()
            snackBarFail("Ingrese su password", regLastName)
        } else {
            wsUserRegister()
        }
    }

    fun wsUserRegister(){
        var raw = UserRegisterRaw()
        raw.phone = phone
        raw.name = regName.text.toString()
        raw.last_name = regLastName.text.toString()
        raw.email = regEmail.text.toString()
        raw.password = regPassword.text.toString()
        usersPresenter.userRegister(raw)
    }

    fun onClickListener(){
        btnRegisterNext.setOnClickListener {
            validateForm()
        }
    }

    override fun usersRegister(userRegister: UserRegister) {
        LogUtils().v(TAG, userRegister.toString())

    }

    override fun showLoading() {
        vLoading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        vLoading.visibility = View.GONE
    }

    override fun getContext(): Context {
        return this
    }

    override fun showMessageError(message: String, type: Int?) {
        LogUtils().v(TAG, message)
        snackBarFail(message, btnRegisterNext)
        vLoading.visibility = View.GONE

    }
}