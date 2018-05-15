package com.gowil.zzleep.repository.dataSource.culqi

import com.google.firebase.auth.FirebaseAuth
import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.raw.CreateOrderRaw
import com.gowil.zzleep.data.entity.raw.CulqiChargeRaw
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.data.entity.response.CreateOrderResponse
import com.gowil.zzleep.data.entity.response.CulqiChargesResponse
import com.gowil.zzleep.data.entity.response.CulqiCreateTokenResponse
import com.gowil.zzleep.data.rest.ApiClient
import com.gowil.zzleep.data.store.FirebaseAuthSession
import com.gowil.zzleep.repository.RepositoryCallBack

import org.json.JSONObject

import java.util.Arrays

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CloudCulqiServicedataStore(private val restApi: ApiClient) : CulqiServiceDataStore {



    override fun createToken(raw: CulqiCreateTokenRaw, callBackCulqi: RepositoryCallBack) {
        val call = ApiClient().getCulqiV2Interface(TOKEN)!!.createToken(raw)
        call.enqueue(object : Callback<CulqiCreateTokenResponse> {
            override fun onResponse(call: Call<CulqiCreateTokenResponse>, response: Response<CulqiCreateTokenResponse>) {
                try {
                    var createTokenResponse: CulqiCreateTokenResponse? = CulqiCreateTokenResponse()
                    if (response.code() == 201) {
                        createTokenResponse = response.body()
                        LogUtils().v(TAG, createTokenResponse!!.toString())
                        callBackCulqi.onSuccess(createTokenResponse)
                    } else if (response.code() == 402) {
                        val responseDataError = response.errorBody()!!.string()
                        LogUtils().v(TAG, " Error: $responseDataError")
                        val json401 = JSONObject(responseDataError)

                        createTokenResponse!!.setError(json401.getString("object").toString(),
                                json401.getString("type").toString(),
                                json401.getString("merchant_message").toString(),
                                if (json401.getString("user_message").toString() == null) "" else json401.getString("user_message").toString(),
                                "")
                        callBackCulqi.onSuccess(createTokenResponse)

                    } else {
                        val responseDataError = response.errorBody()!!.string()
                        LogUtils().v(TAG, " Error: " + response.code() + ": " + responseDataError)

                        var user_message = ""
                        var param = ""

                        val json = JSONObject(responseDataError)

                        if (buscarPlabra(responseDataError, "user_message"))
                            user_message = json.getString("user_message").toString()

                        if (buscarPlabra(responseDataError, "param"))
                            param = json.getString("param").toString()

                        createTokenResponse!!.setError(
                                json.getString("object").toString(),
                                json.getString("type").toString(),
                                json.getString("merchant_message").toString(),
                                user_message,
                                param)
                        callBackCulqi.onSuccess(createTokenResponse)
                    }

                } catch (e: Exception) {
                    callBackCulqi.onFailure(e)
                }

            }

            override fun onFailure(call: Call<CulqiCreateTokenResponse>, t: Throwable) {
                callBackCulqi.onFailure(t)
            }
        })
    }

    override fun charges(raw: CulqiChargeRaw, callBackCulqi: RepositoryCallBack) {
        val call = ApiClient().getCulqiV2Interface(CARGOS)!!.createCharges(raw)
        call.enqueue(object : Callback<CulqiChargesResponse> {
            override fun onResponse(call: Call<CulqiChargesResponse>?, response: Response<CulqiChargesResponse>?) {
                try {
                    var chargesResponse = CulqiChargesResponse()
                    if (response!!.code() == 201){
                        chargesResponse = response!!.body()!!
                        LogUtils().v(TAG, chargesResponse!!.toString())
                        callBackCulqi.onSuccess(chargesResponse)
                    } else {
                        val responseDataError = response.errorBody()!!.string()
                        LogUtils().v(TAG, " Error: " + response.code() + ": " + responseDataError)

                        var user_message = ""
                        var param = ""

                        val json = JSONObject(responseDataError)

                        if (buscarPlabra(responseDataError, "user_message"))
                            user_message = json.getString("user_message").toString()

                        if (buscarPlabra(responseDataError, "param"))
                            param = json.getString("param").toString()

                        chargesResponse!!.setError(
                                json.getString("object").toString(),
                                json.getString("type").toString(),
                                json.getString("merchant_message").toString(),
                                user_message,
                                param)
                        callBackCulqi.onSuccess(chargesResponse)
                    }
                }catch (e: Exception){

                }
            }

            override fun onFailure(call: Call<CulqiChargesResponse>?, t: Throwable?) {

            }

        })
    }

    override fun createOrder(raw: CreateOrderRaw, callBackCulqi: RepositoryCallBack) {

        var user = FirebaseAuth.getInstance().currentUser
        var idToken = "none"
        user!!.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        idToken = task.result.token!!
                        val call = ApiClient().getZzleepTokenInterface("",idToken)!!.setOrder(raw)
                        call.enqueue(object : Callback<CreateOrderResponse> {
                            override fun onFailure(call: Call<CreateOrderResponse>?, t: Throwable?) {
                            }

                            override fun onResponse(call: Call<CreateOrderResponse>?, response: Response<CreateOrderResponse>) {
                                try {
                                    var createOrderResponse = CreateOrderResponse()
                                    if (response!!.code() == 200) {
                                        createOrderResponse = response.body()!!
                                        callBackCulqi.onSuccess(createOrderResponse)
                                    } else {

                                    }
                                }catch (exception: Exception) {

                                }

                            }
                        })

                    } else {


                    }
                }

    }

    fun buscarPlabra(string: String, keyword: String): Boolean {
        LogUtils().v("buscarPlabra ... ", string.replace("\"", " "))
        val found = Arrays.asList(*string.replace("\"", " ").split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()).contains(keyword)
        if (found) {
            LogUtils().v("buscarPlabra", "SI encontro $keyword")
            return true
        } else {
            LogUtils().v("buscarPlabra", "NO encontro $keyword")
            return false

        }
    }

    companion object {
        private val TAG = "CloudCulqi: "
        private val TOKEN = "tokens"
        private val CARGOS = "cargos"
        private val CLIENTE = "cliente"
        private val TARJETA = "tarjeta"
        private val REFUND = "refund"
    }
}
