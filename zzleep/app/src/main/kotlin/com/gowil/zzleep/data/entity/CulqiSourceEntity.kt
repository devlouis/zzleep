package com.gowil.zzleep.data.entity

import java.io.Serializable

class CulqiSourceEntity: Serializable {
    val `object`: String? = null
    val id: String? = null
    val creation_date: String? = null
    val customer_id: String? = null
    val source: CulqiSourceCardEntity? = null
    val iin: CulqiIinEntity? = null
    override fun toString(): String {
        return "CulqiSourceEntity(`object`=$`object`, id=$id, creation_date=$creation_date, customer_id=$customer_id, source=$source, iin=$iin)"
    }


}