package com.gowil.zzleep.data.entity

import java.io.Serializable

class CulqiIinEntity : Serializable{
    var `object`: String? = null
    var bin: String? = null
    var card_brand: String? = null
    var card_type: String? = null
    var card_category: String? = null
    override fun toString(): String {
        return "CulqiIin(`object`=$`object`, bin=$bin, card_brand=$card_brand, card_type=$card_type, card_category=$card_category)"
    }
}