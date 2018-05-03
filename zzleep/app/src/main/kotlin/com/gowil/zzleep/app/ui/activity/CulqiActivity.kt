package com.gowil.zzleep.app.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.gowil.zzleep.R
import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.domain.model.CulqiCreateToken

import com.gowil.zzleep.view.CulqiView

class CulqiActivity: AppCompatActivity(), CulqiView {
    val TAG = javaClass.simpleName

    override fun createToken(culqiCreateToken: CulqiCreateToken) {
        LogUtils().v(TAG, " TOKEN CREADO " + culqiCreateToken.toString())
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessageError(message: String, type: Int?) {

    }

    override fun getContext(): Context {
        return this
    }

    var CulqiPresenter = com.gowil.zzleep.presenter.CulqiPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_culqi)
        initUI()
    }

    fun initUI(){
        CulqiPresenter.attachedView(this)
        val raw = CulqiCreateTokenRaw()
        raw.card_number = "4111111111111111"
        raw.cvv = "123"
        raw.expiration_month = "09"
        raw.expiration_year = "2020"
        raw.email = "louislopez.dev@gmail.com"
        CulqiPresenter.createToken(raw)
    }
}