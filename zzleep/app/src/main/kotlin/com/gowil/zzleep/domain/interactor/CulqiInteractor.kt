package com.gowil.zzleep.domain.interactor

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.CreateOrderRaw
import com.gowil.zzleep.data.entity.raw.CulqiChargeRaw
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.domain.model.CreateOrder
import com.gowil.zzleep.domain.model.CulqiCharges
import com.gowil.zzleep.domain.model.CulqiCreateToken
import com.gowil.zzleep.domain.repository.interactor.CulqiServiceRepository
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack

class CulqiInteractor(private val serviceRepository: CulqiServiceRepository) {

    fun createToken(raw: CulqiCreateTokenRaw, requestCallBackCulqi: RequestCallBack) {
        this.serviceRepository.createTokenRequest(raw, object : RequestCallBack {
            override fun onRequestSuccess(`object`: Any) {

            }

            override fun onRequestSuccess(`object`: Any, type: Int) {
                val culqiCreateToken = `object` as CulqiCreateToken
                LogUtils().v(TAG, " createToken - onRequestSuccess " + culqiCreateToken.toString())
                requestCallBackCulqi.onRequestSuccess(culqiCreateToken, type)
            }

            override fun onRequestFailure(e: Throwable) {

            }

            override fun onRequestFailure(throwable: Throwable, type: Int) {
                requestCallBackCulqi.onRequestFailure(throwable, type)

            }
        })
    }

    fun chargeCulqi(raw: CulqiChargeRaw, requestCallBackCulqi: RequestCallBack){
        this.serviceRepository.chargeRequest(raw, object : RequestCallBack {
            override fun onRequestSuccess(any: Any) {

            }

            override fun onRequestSuccess(any: Any, type: Int) {
                val charges = any as CulqiCharges
                LogUtils().v(TAG, " chargeCulqi - onRequestSuccess " + charges.toString())
                requestCallBackCulqi.onRequestSuccess(charges, type)

            }

            override fun onRequestFailure(e: Throwable) {

            }

            override fun onRequestFailure(throwable: Throwable, type: Int) {
                requestCallBackCulqi.onRequestFailure(throwable, type)
            }
        })
    }

    fun createOrden(raw: CreateOrderRaw, requestCallBackCulqi: RequestCallBack){
        this.serviceRepository.createOrderRequest(raw, object : RequestCallBack {
            override fun onRequestSuccess(any: Any) {

            }

            override fun onRequestSuccess(any: Any, type: Int) {
                var createOrder = any as CreateOrder
                onRequestSuccess(createOrder, type)
            }

            override fun onRequestFailure(e: Throwable) {

            }

            override fun onRequestFailure(throwable: Throwable, type: Int) {

            }
        })
    }

    companion object {
        private val TAG = "CulqiInteractor"
    }
}
