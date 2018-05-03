package com.gowil.zzleep.domain.interactor

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.domain.model.ProductsAudio
import com.gowil.zzleep.domain.model.ProductsVideo
import com.gowil.zzleep.domain.repository.interactor.ProductsServiceRepository
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack

class ProductsInteractor(val serviceRepository: ProductsServiceRepository) {
    companion object {
        private val TAG = "ProductsInteractor"
    }
    fun getProductsVideos(requestCallBack: RequestCallBack){
        this.serviceRepository.productsVideoRequest(object : RequestCallBack{
            override fun onRequestSuccess(any: Any) {

            }

            override fun onRequestSuccess(any: Any, type: Int) {
                val productsVideo = any as MutableList<ProductsVideo>
                LogUtils().v(TAG, " - onRequestSuccess " + productsVideo.toString())
                requestCallBack.onRequestSuccess(productsVideo, type)

            }

            override fun onRequestFailure(e: Throwable) {

            }

            override fun onRequestFailure(throwable: Throwable, type: Int) {
                LogUtils().v(TAG, " onRequestFailure - onRequestSuccess " + throwable.toString())
                requestCallBack.onRequestSuccess(throwable, type)
            }
        })
    }

    fun getProductsAudios(requestCallBack: RequestCallBack){
        this.serviceRepository.productsAudioRequest(object : RequestCallBack {
            override fun onRequestSuccess(any: Any) {

            }

            override fun onRequestSuccess(any: Any, type: Int) {
                val productsAudio = any as ProductsAudio
                LogUtils().v(TAG, " onRequestSuccess " + productsAudio.toString())
                requestCallBack.onRequestSuccess(productsAudio, type)
            }

            override fun onRequestFailure(e: Throwable) {

            }

            override fun onRequestFailure(throwable: Throwable, type: Int) {
                LogUtils().v(TAG, " onRequestFailure - getProductsAudios " + throwable.toString())
                requestCallBack.onRequestSuccess(throwable, type)
            }
        })
    }
}