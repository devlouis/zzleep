package com.gowil.zzleep.data.entity.response

import com.gowil.zzleep.data.entity.CreateOrderEntity
import java.io.Serializable

class CreateOrderResponse: Serializable {
    var msg: String? = ""
    var success: String? = ""
    var order: CreateOrderEntity? = null
    override fun toString(): String {
        return "CreateOrderResponse(msg=$msg, success=$success, data=$order)"
    }


}