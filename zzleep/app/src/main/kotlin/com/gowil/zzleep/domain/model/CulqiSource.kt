package com.gowil.zzleep.domain.model

import java.io.Serializable

class CulqiSource: Serializable {
    var `object`: String? = null
    var id: String? = null
    var type: String? = null
    var creation_date: String? = null
    var customer_id: String? = null
    var source: CulqiSourceCard? = null
    var iin: CulqiIin? = null
    override fun toString(): String {
        return "CulqiSource(`object`=$`object`, id=$id, type=$type, creation_date=$creation_date, customer_id=$customer_id, source=$source, iin=$iin)"
    }


}