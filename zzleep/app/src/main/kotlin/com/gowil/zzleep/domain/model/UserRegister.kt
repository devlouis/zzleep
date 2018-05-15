package com.gowil.zzleep.domain.model

import java.io.Serializable

class UserRegister : Serializable {
    var display_name: String? = ""
    var email: String? = ""
    var photo_url: String? = ""
    var uid: String? = ""
    override fun toString(): String {
        return "UserRegister(display_name=$display_name, email=$email, photo_url=$photo_url, uid=$uid)"
    }


}