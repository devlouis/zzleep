package com.gowil.zzleep.repository.dataSource.products

import android.content.Context
import com.gowil.zzleep.data.rest.ApiClient

class ProductsDataStoreFactory(context: Context) {
    private val context: Context
    companion object {
        val DB = 1
        val CLOUD = 2
        val PREFERENCES = 3
        val IMG_LIBRARY = 4
    }


    init {
        if (context == null) {
            throw IllegalArgumentException("Constructor parameters cannot be null!!!")
        }
        this.context = context.applicationContext
    }

    fun create(dataSource: Int): ProductsServiceDataStore? {
        var culqiServiceDataStore: ProductsServiceDataStore? = null

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

    fun createCloudDataStore(): ProductsServiceDataStore {
        val restApi = ApiClient()
        return CloudProductsServicedataStore()
    }

}