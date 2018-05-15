package com.gowil.zzleep.data.entity

import java.io.Serializable

class CulqiSourceCardEntity: Serializable {
    var `object`: String? = null
    var id: String? = null
    var type: String? = null
    var creation_date: String? = null
    var email: String? = null
    var card_number: String? = null
    var last_four: String? = null
    var active: String? = null
    var iin: CulqiIinEntity? = null
    override fun toString(): String {
        return "CulqiSourceCardEntity(`object`=$`object`, id=$id, type=$type, creation_date=$creation_date, email=$email, card_number=$card_number, last_four=$last_four, active=$active, iin=$iin)"
    }


}