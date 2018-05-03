package com.gowil.zzleep.repository.dataSource.products

import com.gowil.zzleep.repository.RepositoryCallBack

interface ProductsServiceDataStore {
    fun getProductsVideo(repositoryCallBack: RepositoryCallBack)
    fun getProductsAudio(repositoryCallBack: RepositoryCallBack)
}