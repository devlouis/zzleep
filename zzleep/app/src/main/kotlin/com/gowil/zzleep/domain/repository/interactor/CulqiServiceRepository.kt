package com.gowil.zzleep.domain.repository.interactor

import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw


interface CulqiServiceRepository {
    fun createTokenRequest(raw: CulqiCreateTokenRaw, requestCallBackCulqi: RequestCallBack)
}
