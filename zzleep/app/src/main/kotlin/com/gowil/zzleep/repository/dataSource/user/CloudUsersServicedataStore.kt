package com.gowil.zzleep.repository.dataSource.user

import com.google.firebase.auth.FirebaseAuth
import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.UserRegisterRaw
import com.gowil.zzleep.data.entity.response.UserRegisterResponse
import com.gowil.zzleep.data.rest.ApiClient
import com.gowil.zzleep.repository.RepositoryCallBack
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CloudUsersServicedataStore: UsersServiceDataStore {
    val TAG = " CloudUsers"

    override fun setUsers(raw: UserRegisterRaw, repositoryCallBack: RepositoryCallBack) {
        var user = FirebaseAuth.getInstance().currentUser
        var idToken = ""
        user!!.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        idToken = task.result.token!!
                        LogUtils().v(TAG, " getIdToken:: " + idToken!!)
                        val call = ApiClient().getZzleepTokenInterface("",idToken!!)!!.setUser(raw)
                        call.enqueue(object : Callback<UserRegisterResponse> {
                            override fun onFailure(call: Call<UserRegisterResponse>?, t: Throwable?) {

                            }

                            override fun onResponse(call: Call<UserRegisterResponse>, response: Response<UserRegisterResponse>) {
                                try {
                                    var userRegisterResponse: UserRegisterResponse? = null
                                    if (response!!.code() == 200){
                                        userRegisterResponse = response.body()
                                        LogUtils().v(TAG, userRegisterResponse!!.toString())
                                        repositoryCallBack.onSuccess(userRegisterResponse)
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
}