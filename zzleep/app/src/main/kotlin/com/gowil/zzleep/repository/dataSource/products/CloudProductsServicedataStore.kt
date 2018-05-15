package com.gowil.zzleep.repository.dataSource.products

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.response.ProductsVideoResponse
import com.gowil.zzleep.data.rest.ApiClient
import com.gowil.zzleep.repository.RepositoryCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CloudProductsServicedataStore: ProductsServiceDataStore {
    val TAG = "CloudProduct"

    override fun getProductsVideo(repositoryCallBack: RepositoryCallBack) {
        var user = FirebaseAuth.getInstance().currentUser
        var idToken = ""
        user!!.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        idToken = task.result.token!!
                        LogUtils().v(TAG, " getIdToken:: " + idToken!!)
                        val call = ApiClient().getZzleepTokenInterface("",idToken!!)!!.getVideo()
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
                        // Send token to your backend via HTTPS
                        // ...
                    } else {
                        // Handle error -> task.getException();

                    }
                }

    }

    override fun getProductsAudio(repositoryCallBack: RepositoryCallBack) {

    }
}