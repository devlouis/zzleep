package com.gowil.zzleep.view

import com.gowil.zzleep.domain.model.CulqiCreateToken



interface CulqiView: BaseView {
    fun createToken(culqiCreateToken: CulqiCreateToken)
    fun showLoading()
    fun hideLoading()
    fun showMessageError(message: String, type: Int?)
}