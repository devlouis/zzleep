package com.gowil.zzleep.domain.model

import java.io.Serializable

class CulqiCharges: Serializable {
    var version: String? = null
    var duplicated: String? = null
    var `object`: String? = null
    var id: String? = null
    var amount: String? = null
    var currency: String? = null
    var email: String? = null
    var description: String? = null
    var source: CulqiSource? = null
    var date: String? = null
    var reference_code: String? = null
    var net_amount: String? = null
    var response_code: String? = null
    var merchant_message: String? = null
    var user_message: String? = null
    var device_ip: String? = null
    var device_country: String? = null
    var product: String? = null
    var state: String? = null
    var metadata: Metadata? = null
    //TODO - ERROR
    var type: String? = null
    var param: String? = null

    var toString: String? = null
    override fun toString(): String {
        return "CulqiCharges(version=$version, duplicated=$duplicated, `object`=$`object`, id=$id, amount=$amount, currency=$currency, email=$email, description=$description, source=$source, date=$date, reference_code=$reference_code, net_amount=$net_amount, response_code=$response_code, merchant_message=$merchant_message, user_message=$user_message, device_ip=$device_ip, device_country=$device_country, product=$product, state=$state, metadata=$metadata, type=$type, param=$param, toString=$toString)"
    }


}