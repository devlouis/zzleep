package com.gowil.zzleep.data.entity.response

import com.gowil.zzleep.data.entity.ProductsVideoEntity
import java.io.Serializable

class ProductsVideoResponse: Serializable {
    val data: List<ProductsVideoEntity>? = null
    override fun toString(): String {
        return "ProductsVideoResponse(data=$data)"
    }

}