package com.gowil.zzleep.data.mapper

import com.gowil.zzleep.data.entity.response.ProductsVideoResponse
import com.gowil.zzleep.domain.model.ProductsVideo
import java.util.ArrayList

class ProductsDataMapper {

    fun productsVideoTransWalkingS(response: ProductsVideoResponse): MutableList<ProductsVideo>{
        var productsVideoList: MutableList<ProductsVideo> = ArrayList()
        for (i in 0 until response.data!!.size){
            var productsVideo = ProductsVideo()
            productsVideo.amount = if (response.data!![i].amount != null) response.data!![i].amount else ""
            productsVideo.company_id = if (response.data!![i].company_id != null) response.data!![i].company_id else -1
            productsVideo.description = if (response.data!![i].description != null) response.data!![i].description else ""
            productsVideo.discount = if (response.data!![i].discount != null) response.data!![i].discount else ""
            productsVideo.id = if (response.data!![i].id != null) response.data!![i].id else 0
            productsVideo.image = if (response.data!![i].image != null) response.data!![i].image else ""
            productsVideo.name = if (response.data!![i].name != null) response.data!![i].name else ""
            productsVideo.preview = if (response.data!![i].preview != null) response.data!![i].preview else ""
            productsVideoList.add(productsVideo)
        }

        return productsVideoList
    }
}