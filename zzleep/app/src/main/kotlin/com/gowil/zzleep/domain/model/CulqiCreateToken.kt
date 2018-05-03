package com.gowil.zzleep.domain.model

import java.io.Serializable

class CulqiCreateToken: Serializable {
    var `object`: String? = null
    var id: String? = null
    var type: String? = null
    var email: String? = null
    var creation_date: String? = null
    var card_number: String? = null
    var last_four: String? = null
    var active: String? = null
    var iin: CulqiIin? = null
    var metadata: Metadata? = null

    var merchant_message: String? = null
    var user_message: String? = null
    var param: String? = null
    override fun toString(): String {
        return "CulqiCreateToken(`object`=$`object`, id=$id, type=$type, email=$email, creation_date=$creation_date, card_number=$card_number, last_four=$last_four, active=$active, iin=$iin, metadata=$metadata, merchant_message=$merchant_message, user_message=$user_message, param=$param)"
    }


}