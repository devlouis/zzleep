package com.gowil.zzleep.data.entity.raw

import com.gowil.zzleep.data.entity.AntifraudDetailsEntity
import com.gowil.zzleep.domain.model.Metadata
import java.io.Serializable

class CulqiChargeRaw: Serializable {
    var amount: String? = null
    var currency_code: String? = null
    var email: String? = null
    var source_id: String? = null
    var merchant: String? = null
    var description: String? = null
    var metadata: Metadata? = null
    var antifraud_details: AntifraudDetailsEntity? = null
    override fun toString(): String {
        return "CulqiChargeRaw(amount=$amount, currency_code=$currency_code, email=$email, source_id=$source_id, merchant=$merchant, description=$description, metadata=$metadata, antifraud_details=$antifraud_details)"
    }


}