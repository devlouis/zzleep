package com.gowil.zzleep.domain.repository.interactor

interface ProductsServiceRepository {
    fun productsVideoRequest(requestCallBack: RequestCallBack)
    fun productsAudioRequest(requestCallBack: RequestCallBack)
}