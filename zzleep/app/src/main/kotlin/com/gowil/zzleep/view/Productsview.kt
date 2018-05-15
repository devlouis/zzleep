package com.gowil.zzleep.view

import com.gowil.zzleep.domain.model.ProductsAudio
import com.gowil.zzleep.domain.model.ProductsVideo

interface Productsview : BaseView{
    fun getalarmVideos(productsVideos: MutableList<ProductsVideo>)
    fun getalarmAudio(productsAudio: ProductsAudio)
    fun showLoading()
    fun hideLoading()

}