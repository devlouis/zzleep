package com.gowil.zzleep.presenter


import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.CreateOrderRaw
import com.gowil.zzleep.data.entity.raw.CulqiChargeRaw
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.data.entity.response.CreateOrderResponse
import com.gowil.zzleep.data.mapper.CulqiDataMapper
import com.gowil.zzleep.data.store.ConstantsTypeServices
import com.gowil.zzleep.domain.interactor.CulqiInteractor
import com.gowil.zzleep.domain.model.CreateOrder
import com.gowil.zzleep.domain.model.CulqiCharges
import com.gowil.zzleep.domain.repository.interactor.CulqiServiceRepository
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack
import com.gowil.zzleep.view.CulqiView
import com.gowil.zzleep.repository.culqi.CulqiDataRepository
import com.gowil.zzleep.repository.dataSource.culqi.CulqiDataStoreFactory
import com.gowil.zzleep.domain.model.CulqiCreateToken




class CulqiPresenter: Presenter<CulqiView>, RequestCallBack {


    val TAG = "CulqiPresenter: "
    var culqiView: CulqiView? = null
    var culqiInteractor: CulqiInteractor? = null
    var culqiServiceRepository: CulqiServiceRepository? = null

    fun createToken(raw: CulqiCreateTokenRaw) {
        LogUtils().v(TAG, raw.toString())
        culqiView!!.showLoading()
        culqiInteractor!!.createToken(raw, this)
    }

    fun chargeCulqi(raw: CulqiChargeRaw){
        LogUtils().v(TAG, raw.toString())
        culqiView!!.showLoading()
        culqiInteractor!!.chargeCulqi(raw, this)

    }

    fun createOrden(raw: CreateOrderRaw) {
        LogUtils().v(TAG, raw.toString())
        culqiView!!.showLoading()
        culqiInteractor!!.createOrden(raw, this)
    }

    override fun onRequestSuccess(`object`: Any) {

    }

    override fun onRequestSuccess(any: Any, type: Int) {
        when(type){
            ConstantsTypeServices().CULQI_CREATE_TOKEN -> {
                val culqiCreateToken = any as CulqiCreateToken
                culqiView!!.createToken(culqiCreateToken)
            }
            ConstantsTypeServices().CULQI_CHARGE -> {
                val culqiCharges = any as CulqiCharges
                culqiView!!.chargeCulqi(culqiCharges)
            }
            ConstantsTypeServices().CREATE_ORDER -> {
                val createOrder = any as CreateOrder
                culqiView!!.createOrder(createOrder)
            }
        }
    }

    override fun onRequestFailure(e: Throwable) {

    }

    override fun onRequestFailure(throwable: Throwable, type: Int) {

    }

    override fun attachedView(view: CulqiView) {
        culqiView = view
        culqiServiceRepository = CulqiDataRepository(CulqiDataStoreFactory(culqiView!!.getContext()), CulqiDataMapper())
        culqiInteractor = CulqiInteractor(culqiServiceRepository!!)
    }

    override fun removeView() {
        culqiView = null
    }


}