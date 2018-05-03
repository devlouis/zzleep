package com.gowil.zzleep.data.entity.raw

import java.io.Serializable

class CulqiCreateTokenRaw : Serializable {
    var card_number: String? = null
    var cvv: String? = null
    var expiration_month: String? = null
    var expiration_year: String? = null
    var email: String? = null
    override fun toString(): String {
        return "CulqiCreateTokenRaw(card_number=$card_number, cvv=$cvv, expiration_month=$expiration_month, expiration_year=$expiration_year, email=$email)"
    }


}