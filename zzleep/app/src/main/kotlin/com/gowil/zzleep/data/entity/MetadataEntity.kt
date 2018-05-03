package com.gowil.zzleep.data.entity

import java.io.Serializable

class MetadataEntity : Serializable {
    var id_users: String? = null
    var id_orders: String? = null
    var id_store: String? = null
    var name_store: String? = null
    override fun toString(): String {
        return "Metadata(id_users=$id_users, id_orders=$id_orders, id_store=$id_store, name_store=$name_store)"
    }


}