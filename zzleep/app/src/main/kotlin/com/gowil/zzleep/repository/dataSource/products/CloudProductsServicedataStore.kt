package com.gowil.zzleep.repository.dataSource.products

import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.response.ProductsVideoResponse
import com.gowil.zzleep.data.rest.ApiClient
import com.gowil.zzleep.repository.RepositoryCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CloudProductsServicedataStore: ProductsServiceDataStore {
    val TAG = "CloudProductsServicedataStore"

    override fun getProductsVideo(repositoryCallBack: RepositoryCallBack) {
        val call = ApiClient().getZzleepTokenInterface("","eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ5NGQ1ZjMyZTE4NmRjMWUxNjA0MjhiZDdhODE1NDI2ZjI3NDg4MmIifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20venpsZWVwLTRlMmIxIiwibmFtZSI6Ik1lbGlzc2F8QXBvbGF5YSIsInBpY3R1cmUiOiJodHRwczovL2dyYXBoLmZhY2Vib29rLmNvbS8xMDIxNjU5NTgxNzkxMTcwMS9waWN0dXJlP2hlaWdodD0yMDAmd2lkdGg9MjAwJm1pZ3JhdGlvbl9vdmVycmlkZXM9JTdCb2N0b2Jlcl8yMDEyJTNBdHJ1ZSU3RCIsImF1ZCI6Inp6bGVlcC00ZTJiMSIsImF1dGhfdGltZSI6MTUyNTAyMDMxOCwidXNlcl9pZCI6IkhnbUhzTndYTE9UUU1PRlZHNzRQczdsNUVDbzIiLCJzdWIiOiJIZ21Ic053WExPVFFNT0ZWRzc0UHM3bDVFQ28yIiwiaWF0IjoxNTI1MzI5MDM0LCJleHAiOjE1MjUzMzI2MzQsImVtYWlsIjoibWVsaXNzYV8xNF84MEBob3RtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJmYWNlYm9vay5jb20iOlsiMTAyMTY1OTU4MTc5MTE3MDEiXSwiZW1haWwiOlsibWVsaXNzYV8xNF84MEBob3RtYWlsLmNvbSJdfSwic2lnbl9pbl9wcm92aWRlciI6InBhc3N3b3JkIn19.Qb7Npegs6VvWYG_S16lBsdvVxHLut36kqubUfu4ljw2HRV9osTqotOMSJf7BooYHGHnfl4Lpz5N1f83zUh_BY8QUD0JfFYHUfTxiS1PcX55Oak7_CEVrvh2qwVvz9-XqvLJnAlK2R6etyLHolz9pdK5BHzUobawkFmoOzIz26sMcZg5jRxPMSSBYp90MTQsNy76mtVwAckxMWoOCFA-sB6ULOhl181AESgRfqPUSVtswwUA5xuthoooFvdwH3OqOILJ5L1Ue-R2Ueh5C7vtS6v0trjcujnYLZlwqX66NdgYVE482fjGptuWakuf0NNyX38y3iM4SRTT732rhkcvhGQ")!!.getVideo()
        call.enqueue(object : Callback<ProductsVideoResponse> {
            override fun onFailure(call: Call<ProductsVideoResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<ProductsVideoResponse>, response: Response<ProductsVideoResponse>) {
                try {
                    var productsVideoResponse: ProductsVideoResponse? = null
                    if (response!!.code() == 200){
                        productsVideoResponse = response.body()
                        LogUtils().v(TAG, productsVideoResponse!!.toString())
                        repositoryCallBack.onSuccess(productsVideoResponse)
                    }else{

                    }
                }catch (e: Exception){

                }
            }
        })
    }

    override fun getProductsAudio(repositoryCallBack: RepositoryCallBack) {

    }
}