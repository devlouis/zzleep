package com.gowil.zzleep.data.entity

import java.io.Serializable

class AntifraudDetailsEntity: Serializable {
    var country_code: String? = null
    var first_name: String? = null
    var last_name: String? = null
    var address_city: String? = null
    var address: String? = null
    var email: String? = null
    var phone: String? = null
    var phone_number: String? = null
    var `object`: String? = null
    override fun toString(): String {
        return "AntifraudDetailsEntity(country_code=$country_code, first_name=$first_name, last_name=$last_name, address_city=$address_city, address=$address, email=$email, phone=$phone, phone_number=$phone_number, `object`=$`object`)"
    }


}