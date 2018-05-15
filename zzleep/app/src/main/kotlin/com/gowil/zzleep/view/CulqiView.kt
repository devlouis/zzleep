package com.gowil.zzleep.view

import com.gowil.zzleep.domain.model.CreateOrder
import com.gowil.zzleep.domain.model.CulqiCharges
import com.gowil.zzleep.domain.model.CulqiCreateToken



interface CulqiView: BaseView {
    fun createToken(culqiCreateToken: CulqiCreateToken)
    fun chargeCulqi(culqiCharges: CulqiCharges)
    fun createOrder(createOrder: CreateOrder)
    fun showLoading()
    fun hideLoading()
}