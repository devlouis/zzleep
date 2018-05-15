package com.gowil.zzleep.repository.dataSource.user

import android.content.Context

class UsersDataStoreFactory(context: Context) {
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

    fun create(dataSource: Int): UsersServiceDataStore {
        var useersServiceDataStore: UsersServiceDataStore? = null
        when(dataSource){
            CLOUD -> useersServiceDataStore = createCloudDataStore()
            DB -> {
            }
            PREFERENCES -> {
            }
            IMG_LIBRARY -> {
            }
        }
        return createCloudDataStore()
    }

    fun createCloudDataStore() : UsersServiceDataStore {
        return CloudUsersServicedataStore()
    }

}