package com.gowil.zzleep.repository.products

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.response.ProductsVideoResponse
import com.gowil.zzleep.data.mapper.ProductsDataMapper
import com.gowil.zzleep.data.store.ConstantsTypeServices
import com.gowil.zzleep.domain.repository.interactor.ProductsServiceRepository
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack
import com.gowil.zzleep.repository.RepositoryCallBack
import com.gowil.zzleep.repository.dataSource.products.ProductsDataStoreFactory

class ProductsDataRepository(val dataStoreFactory: ProductsDataStoreFactory, val mapper: ProductsDataMapper): ProductsServiceRepository {

    override fun productsVideoRequest(requestCallBack: RequestCallBack) {
        val serviceDataStore = this.dataStoreFactory.create(ProductsDataStoreFactory.CLOUD)
        serviceDataStore!!.getProductsVideo(object : RepositoryCallBack {
            override fun onSuccess(obj: Any) {
                val productsVideos = mapper.productsVideoTransWalkingS(obj as ProductsVideoResponse)
                LogUtils().v(TAG, productsVideos.toString())
                requestCallBack.onRequestSuccess(productsVideos, ConstantsTypeServices().PRODUCTS_VIDEO)
            }

            override fun onSuccess(obj: Any, header: Any) {
            }

            override fun onFailure(throwable: Throwable) {
                requestCallBack.onRequestFailure(throwable, ConstantsTypeServices().PRODUCTS_VIDEO)
            }
        })

    }

    override fun productsAudioRequest(requestCallBack: RequestCallBack) {

    }

    companion object {
        private val TAG = "ProductsDataRepository: "
    }

}