package com.gowil.zzleep.data.entity

import java.io.Serializable

/**
 * create by Luis
 * "amount": "0.00",
"company_id": 1,
"created_at": "Wed, 27 Sep 2017 18:39:39 GMT",
"description": "Alarma de saico",
"discount": "0.00",
"id": 1,
"image": "alarmas/Saico.png",
"is_owned": 0,
"name": "Saico",
"points": 0,
"preview": "alarmas/Saico.mp4",
"updated_at": "Wed, 27 Sep 2017 18:39:39 GMT"
 */
class ProductsVideoEntity: Serializable {
    var amount: String? = ""
    var company_id: Int? = -1
    var created_at: String? = ""
    var description: String? = ""
    var discount: String? = ""
    var id: Int? = 0
    var image: String? = ""
    var is_owned: Int? = 0
    var name: String? = ""
    var points: Int? = 0
    var preview: String? = ""
    override fun toString(): String {
        return "ProductsVideoEntity(amount=$amount, company_id=$company_id, created_at=$created_at, description=$description, discount=$discount, id=$id, image=$image, is_owned=$is_owned, name=$name, points=$points, preview=$preview)"
    }

}