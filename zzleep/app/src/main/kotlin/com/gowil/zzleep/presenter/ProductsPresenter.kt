package com.gowil.zzleep.presenter

import com.gowil.zzleep.data.mapper.ProductsDataMapper
import com.gowil.zzleep.data.store.ConstantsTypeServices
import com.gowil.zzleep.domain.interactor.ProductsInteractor
import com.gowil.zzleep.domain.model.ProductsAudio
import com.gowil.zzleep.domain.model.ProductsVideo
import com.gowil.zzleep.domain.repository.interactor.ProductsServiceRepository
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack
import com.gowil.zzleep.repository.dataSource.products.ProductsDataStoreFactory
import com.gowil.zzleep.repository.products.ProductsDataRepository
import com.gowil.zzleep.view.Productsview

class ProductsPresenter: Presenter<Productsview>, RequestCallBack {
    val TAG = "ProductsPresenter: "
    var view: Productsview? = null
    var interactor: ProductsInteractor? = null
    var serviceRepository: ProductsServiceRepository? = null


    fun getAlarmVideos(){
        view!!.showLoading()
        interactor!!.getProductsVideos(this)
    }


    override fun attachedView(view: Productsview) {
        this.view = view
        serviceRepository = ProductsDataRepository(ProductsDataStoreFactory(this.view!!.getContext()), ProductsDataMapper())
        interactor = ProductsInteractor(serviceRepository!!)
    }

    override fun removeView() {
        view = null
    }

    override fun onRequestSuccess(any: Any) {

    }

    override fun onRequestSuccess(any: Any, type: Int) {
        when(type){
            ConstantsTypeServices().PRODUCTS_VIDEO -> {
                val productsVideos = any as MutableList<ProductsVideo>
                view!!.getalarmVideos(productsVideos)
            }
            ConstantsTypeServices().PRODUCTS_AUDIO -> {
                val productsAudio = any as ProductsAudio
                view!!.getalarmAudio(productsAudio)
            }
        }
    }

    override fun onRequestFailure(e: Throwable) {

    }

    override fun onRequestFailure(throwable: Throwable, type: Int) {

    }
}