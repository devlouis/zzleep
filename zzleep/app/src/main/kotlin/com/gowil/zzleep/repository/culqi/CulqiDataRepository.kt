package com.gowil.zzleep.repository.culqi

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.data.entity.response.CulqiCreateTokenResponse
import com.gowil.zzleep.data.mapper.CulqiDataMapper
import com.gowil.zzleep.data.store.ConstantsTypeServices
import com.gowil.zzleep.domain.repository.interactor.CulqiServiceRepository
import com.gowil.zzleep.domain.repository.interactor.RequestCallBack
import com.gowil.zzleep.repository.RepositoryCallBack
import com.gowil.zzleep.repository.dataSource.culqi.CulqiDataStoreFactory

class CulqiDataRepository(private val culqiDataStoreFactory: CulqiDataStoreFactory, private val mapper: CulqiDataMapper) : CulqiServiceRepository {

    override fun createTokenRequest(raw: CulqiCreateTokenRaw, requestCallBackCulqi: RequestCallBack) {
        val serviceDataStore = this.culqiDataStoreFactory.create(CulqiDataStoreFactory.CLOUD)
        serviceDataStore!!.createToken(raw, object : RepositoryCallBack {
            override fun onSuccess(`object`: Any) {
                val culqiCreateToken = mapper.createTokenTransWalkingS(`object` as CulqiCreateTokenResponse)
                LogUtils().v(TAG, culqiCreateToken!!.toString())
                requestCallBackCulqi.onRequestSuccess(culqiCreateToken, ConstantsTypeServices().CULQI_CREATE_TOKEN)
            }

            override fun onSuccess(response: Any, header: Any) {

            }

            override fun onFailure(throwable: Throwable) {

            }
        })
    }

    companion object {
        private val TAG = "CulqiDataRepository: "
    }
}
