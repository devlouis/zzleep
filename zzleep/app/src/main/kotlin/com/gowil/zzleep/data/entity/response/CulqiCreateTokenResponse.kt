package com.gowil.zzleep.data.entity.response

import com.gowil.zzleep.data.entity.CulqiIinEntity
import com.gowil.zzleep.data.entity.MetadataEntity

class CulqiCreateTokenResponse: BaseResponse() {

    var `object`: String? = null
    var id: String? = null
    var type: String? = null
    var email: String? = null
    var creation_date: String? = null
    var card_number: String? = null
    var last_four: String? = null
    var active: String? = null
    var iin: CulqiIinEntity? = null
    var metadata: MetadataEntity? = null

    var merchant_message: String? = null
    var user_message: String? = null
    var param: String? = null

    fun setError(`object`: String, type: String, merchant_message: String, user_message: String, param: String) {
        this.`object` = `object`
        this.type = type
        this.merchant_message = merchant_message
        this.user_message = user_message
        this.param = param
    }
    override fun toString(): String {
        return "CulqiCreateTokenResponse(`object`=$`object`, id=$id, type=$type, email=$email, creation_date=$creation_date, card_number=$card_number, last_four=$last_four, active=$active, iin=$iin, metadata=$metadata, merchant_message=$merchant_message, user_message=$user_message, param=$param)"
    }


}