package com.gowil.zzleep.data.entity.raw

import java.io.Serializable

/**
 * {
"email": "melissa_14_80@gmail.com",
"name": "Melissa",
"password": "123qwe",
"last_name": "Apolaya",
"phone": "970517329"
}
 */
class UserRegisterRaw: Serializable {
    var email: String? = ""
    var name: String? = ""
    var password: String? = ""
    var last_name: String? = ""
    var phone: String? = ""
    override fun toString(): String {
        return "UserRegisterRaw(email=$email, name=$name, password=$password, last_name=$last_name, phone=$phone)"
    }


}