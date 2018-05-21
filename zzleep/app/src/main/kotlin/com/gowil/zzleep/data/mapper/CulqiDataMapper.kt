package com.gowil.zzleep.data.mapper

import com.gowil.zzleep.data.entity.response.CulqiCreateTokenResponse
import com.gowil.zzleep.data.entity.CulqiIinEntity
import android.support.v4.view.MotionEventCompat.getSource
import android.support.v4.view.accessibility.AccessibilityRecordCompat.setSource
import com.gowil.zzleep.data.entity.response.CulqiChargesResponse
import android.support.v4.view.MotionEventCompat.getSource
import android.support.v4.view.accessibility.AccessibilityRecordCompat.setSource
import com.gowil.zzleep.data.entity.CulqiSourceEntity
import com.gowil.zzleep.data.entity.MetadataEntity
import com.gowil.zzleep.data.entity.CulqiSourceCardEntity
import com.gowil.zzleep.data.entity.response.CreateOrderResponse
import com.gowil.zzleep.domain.model.*


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

    fun culqiChargesTransWalkingS(response: CulqiChargesResponse): CulqiCharges? {
        var culqiCharges: CulqiCharges? = null
        try {
            culqiCharges = CulqiCharges()
            culqiCharges.duplicated = response.duplicated
            culqiCharges.`object` = response.`object`
            culqiCharges.id = response.id
            culqiCharges.amount = response.amount
            culqiCharges.currency = response.currency
            culqiCharges.email = response.email
            culqiCharges.description = response.description
            culqiCharges.source = culqiSourceTransWalkingS(response.source!!)
            culqiCharges.date = response.date
            culqiCharges.reference_code = response.reference_code
            culqiCharges.net_amount = response.net_amount
            culqiCharges.response_code = response.response_code
            culqiCharges.merchant_message = if (response.merchant_message == null) "" else response.merchant_message
            culqiCharges.user_message = if (response.user_message == null) "" else response.user_message
            culqiCharges.device_ip = response.device_ip
            culqiCharges.device_country = response.device_country
            culqiCharges.product = response.product
            culqiCharges.state = response.state
            culqiCharges.metadata = metadataTransWalkingS(response.metadata!!)
            culqiCharges.type = response.type
            culqiCharges.param = response.param
            culqiCharges.toString = response.toString()
        } catch (e: Exception) {

        }

        return culqiCharges
    }

    fun culqiSourceTransWalkingS(entity: CulqiSourceEntity): CulqiSource? {
        var culqiSource: CulqiSource? = null
        try {
            culqiSource = CulqiSource()
            culqiSource.`object` = entity.`object`
            culqiSource.id = entity.id
            culqiSource.creation_date = entity.creation_date
            culqiSource.source = culqiSourceCardTransWalkingS(entity.source!!)
            culqiSource.iin = culqiIinTransWalkingS(entity.iin!!)
        } catch (e: Exception) {

        }

        return culqiSource
    }

    fun metadataTransWalkingS(metadataEntity: MetadataEntity): com.gowil.zzleep.domain.model.Metadata? {
        val metadata: com.gowil.zzleep.domain.model.Metadata? = null
        try {
            metadata!!.id_orders = metadataEntity.id_orders
            metadata.id_users = metadataEntity.id_users
        } catch (e: Exception) {

        }

        return metadata
    }

    fun culqiSourceCardTransWalkingS(entity: CulqiSourceCardEntity): CulqiSourceCard? {
        var culqiSourceCard: CulqiSourceCard? = null

        try {
            culqiSourceCard = CulqiSourceCard()
            culqiSourceCard.`object` = entity.`object`
            culqiSourceCard.id = entity.id
            culqiSourceCard.type = entity.type
            culqiSourceCard.creation_date = entity.creation_date
            culqiSourceCard.email = entity.email
            culqiSourceCard.card_number = entity.card_number
            culqiSourceCard.last_four = entity.last_four
            culqiSourceCard.active = entity.active
            culqiSourceCard.iin = culqiIinTransWalkingS(entity.iin!!)
        } catch (e: Exception) {

        }

        return culqiSourceCard
    }

    fun createOrderTRansWalkingS(response: CreateOrderResponse) : CreateOrder{
        var createOrder: CreateOrder? = null
        try {
            createOrder = CreateOrder()
            createOrder.amount = response.order!!.amount
            createOrder.order_id = response.order!!.order_id
            createOrder.order_status = response.order!!.order_status
            createOrder.user_id = response.order!!.user_id

        } catch (e : Exception) {

        }
        return createOrder!!
    }
}