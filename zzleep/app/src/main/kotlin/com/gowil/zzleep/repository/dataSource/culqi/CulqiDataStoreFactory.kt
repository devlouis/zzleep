package com.gowil.zzleep.repository.dataSource.culqi

import android.content.Context

import com.gowil.zzleep.data.rest.ApiClient

class CulqiDataStoreFactory(context: Context?) {
    private val context: Context

    init {
        if (context == null) {
            throw IllegalArgumentException("Constructor parameters cannot be null!!!")
        }
        this.context = context.applicationContext
    }

    fun create(dataSource: Int): CulqiServiceDataStore? {
        var culqiServiceDataStore: CulqiServiceDataStore? = null

        when (dataSource) {
            CLOUD -> culqiServiceDataStore = createCloudDataStore()
            DB -> {
            }
            PREFERENCES -> {
            }
            IMG_LIBRARY -> {
            }
        }//detailWalkingServiceDataStore= new DbDetailWalkingServiceDataStore();
        //detailWalkingServiceDataStore = new PrefDetailWalkingServiceDataStore(context,new PreferencesHelper());
        //detailWalkingServiceDataStore = new ImageLibraryWalkingServiceDataStore(context, new ImageHelper());
        return culqiServiceDataStore
    }

    fun createCloudDataStore(): CulqiServiceDataStore {
        val restApi = ApiClient()
        return CloudCulqiServicedataStore(restApi)
    }

    companion object {
        val DB = 1
        val CLOUD = 2
        val PREFERENCES = 3
        val IMG_LIBRARY = 4
    }
}
