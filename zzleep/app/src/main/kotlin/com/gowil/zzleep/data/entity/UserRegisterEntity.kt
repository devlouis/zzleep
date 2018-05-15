package com.gowil.zzleep.data.entity

import java.io.Serializable

/**
"display_name": "Melissa Apolaya",
"email": "melissa_14_80@gmail.com",
"photo_url": null,
"uid": "eybPMOLafIb2G98PI3CCk8vrwXu1"
 */
class UserRegisterEntity:Serializable {
    var display_name: String? = ""
    var email: String? = ""
    var photo_url: String? = ""
    var uid: String? = ""
    override fun toString(): String {
        return "UserRegisterEntity(display_name=$display_name, email=$email, photo_url=$photo_url, uid=$uid)"
    }


}