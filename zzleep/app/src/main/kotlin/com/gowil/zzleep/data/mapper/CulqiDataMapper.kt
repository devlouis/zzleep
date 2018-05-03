package com.gowil.zzleep.data.mapper

import com.gowil.zzleep.domain.model.CulqiCreateToken
import com.gowil.zzleep.data.entity.response.CulqiCreateTokenResponse
import com.gowil.zzleep.domain.model.CulqiIin
import com.gowil.zzleep.data.entity.CulqiIinEntity





class CulqiDataMapper {

    fun createTokenTransWalkingS(response: CulqiCreateTokenResponse): CulqiCreateToken? {
        var culqiCreateToken: CulqiCreateToken? = null
        try {
            culqiCreateToken = CulqiCreateToken()
            culqiCreateToken.`object` = response.`object`
            culqiCreateToken.id = response.id
            culqiCreateToken.active = response.active
            culqiCreateToken.email = response.email
            culqiCreateToken.creation_date = response.id
            culqiCreateToken.card_number = response.card_number
            culqiCreateToken.last_four = response.last_four
            culqiCreateToken.active = response.active
            culqiCreateToken.iin = culqiIinTransWalkingS(response.iin!!)
            //culqiCreateToken.setMetadata(metadataTransWalkingS(response.getMetadata()));
            culqiCreateToken.type = response.type
            culqiCreateToken.param = response.param
            culqiCreateToken.merchant_message = response.merchant_message
            culqiCreateToken.user_message = response.user_message

        } catch (e: Exception) {

        }

        return culqiCreateToken
    }

    fun culqiIinTransWalkingS(culqiIinEntity: CulqiIinEntity): CulqiIin? {
        var culqiIin: CulqiIin? = null
        try {
            culqiIin = CulqiIin()
            culqiIin.`object` = culqiIinEntity.`object`
            culqiIin.card_brand = culqiIinEntity.card_brand
            culqiIin.bin = culqiIinEntity.bin
            culqiIin.card_type = culqiIinEntity.card_type
            culqiIin.card_category = culqiIinEntity.card_category
        } catch (e: Exception) {

        }

        return culqiIin
    }
}