package com.gowil.zzleep.domain.model

import java.io.Serializable

/**
 *
"amount": "1.00",
"order_id": 6,
"order_status": 1,
"user_id": 8
 */
class CreateOrder: Serializable {
    var amount: String? = ""
    var order_id: String? = ""
    var order_status: String? = ""
    var user_id: String? = ""
    override fun toString(): String {
        return "CreateOrder(amount=$amount, order_id=$order_id, order_status=$order_status, user_id=$user_id)"
    }


}