package com.gowil.zzleep.domain.model

import java.io.Serializable

class ProductsAudio: Serializable {
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
        return "ProductsAudioEntity(amount=$amount, company_id=$company_id, created_at=$created_at, description=$description, discount=$discount, id=$id, image=$image, is_owned=$is_owned, name=$name, points=$points, preview=$preview)"
    }

}