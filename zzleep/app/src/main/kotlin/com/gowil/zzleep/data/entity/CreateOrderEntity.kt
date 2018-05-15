package com.gowil.zzleep.data.entity

import java.io.Serializable

class CreateOrderEntity: Serializable {
    var amount: String? = ""
    var order_id: String? = ""
    var order_status: String? = ""
    var user_id: String? = ""
    override fun toString(): String {
        return "CreateOrder(amount=$amount, order_id=$order_id, order_status=$order_status, user_id=$user_id)"
    }
}