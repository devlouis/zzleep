package com.gowil.zzleep.domain.repository.interactor

import com.gowil.zzleep.data.entity.raw.CreateOrderRaw
import com.gowil.zzleep.data.entity.raw.CulqiChargeRaw
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw


interface CulqiServiceRepository {
    fun createTokenRequest(raw: CulqiCreateTokenRaw, requestCallBackCulqi: RequestCallBack)
    fun chargeRequest(raw: CulqiChargeRaw, requestCallBackCulqi: RequestCallBack)
    fun createOrderRequest(raw: CreateOrderRaw, requestCallBackCulqi: RequestCallBack)
}
