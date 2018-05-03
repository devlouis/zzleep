package com.gowil.zzleep.repository.dataSource.culqi

import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.repository.RepositoryCallBack

interface CulqiServiceDataStore {
    fun createToken(raw: CulqiCreateTokenRaw, callBackCulqi: RepositoryCallBack)
}
