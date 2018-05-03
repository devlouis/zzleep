package com.gowil.zzleep.data.entity.response

import java.io.Serializable

open class BaseResponse: Serializable {
    val STATE_SUCCESS = 1
    val status: Int = 0
    val msg: String? = null
    override fun toString(): String {
        return "BaseResponse(STATE_SUCCESS=$STATE_SUCCESS, status=$status, msg=$msg)"
    }
    //val data_error: List<BaseError>? = null


}