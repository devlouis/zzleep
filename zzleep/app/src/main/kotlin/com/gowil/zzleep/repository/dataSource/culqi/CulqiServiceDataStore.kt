package com.gowil.zzleep.repository.dataSource.culqi

import com.gowil.zzleep.data.entity.raw.CreateOrderRaw
import com.gowil.zzleep.data.entity.raw.CulqiChargeRaw
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.repository.RepositoryCallBack

interface CulqiServiceDataStore {
    fun createToken(raw: CulqiCreateTokenRaw, callBackCulqi: RepositoryCallBack)
    fun charges(raw: CulqiChargeRaw, callBackCulqi: RepositoryCallBack)
    fun createOrder(raw: CreateOrderRaw, callBackCulqi: RepositoryCallBack)
}
