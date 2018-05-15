package com.gowil.zzleep.data.entity.raw

import java.io.Serializable

/**
 *
 * {
"id_status" : "1",
"id_charge" : "chr_live_kEazTaQBDtzNdwFr",
"total" : "1.00",
"id_user" : "xxx",
"id_product" : "xxx"
"response_culqi: "String formato JSON"
}
 */

class CreateOrderRaw: Serializable {
    var id_status: String? = ""
    var id_charge: String? = ""
    var total: String? = ""
    var id_user: String? = ""
    var id_product: String? = ""
    var response_culqi: String? = ""
    override fun toString(): String {
        return "CreateOrderRaw(id_status=$id_status, id_charge=$id_charge, total=$total, id_user=$id_user, id_product=$id_product, response_culqi=$response_culqi)"
    }


}