package com.gowil.zzleep.app.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.gowil.zzleep.R
import com.gowil.zzleep.app.core.BaseAppCompat
import com.gowil.zzleep.app.core.utils.LogUtils
import com.gowil.zzleep.data.entity.AntifraudDetailsEntity
import com.gowil.zzleep.data.entity.raw.CreateOrderRaw
import com.gowil.zzleep.data.entity.raw.CulqiChargeRaw
import com.gowil.zzleep.data.entity.raw.CulqiCreateTokenRaw
import com.gowil.zzleep.domain.model.CreateOrder
import com.gowil.zzleep.domain.model.CulqiCharges
import com.gowil.zzleep.domain.model.CulqiCreateToken
import com.gowil.zzleep.domain.model.Metadata

import com.gowil.zzleep.view.CulqiView
import kotlinx.android.synthetic.main.activity_culqi.*

class CulqiActivity: BaseAppCompat(), CulqiView {

    val SUCCESS_CULQI_TOKEN = "token"
    val SUCCESS_CULQI_CARGO = "cargo"
    val TAG = javaClass.simpleName

    var userid = ""
    var tokenCode = ""
    var nombre = ""
    var precio = ""
    var userToken = ""
    var email = ""
    var id: Int? = null
    var productID = "2"

    var ID_SOURCE = ""


    var culqiPresenter = com.gowil.zzleep.presenter.CulqiPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_culqi)
        getBundle()
        initUI()
    }

    fun getBundle(){
        if (intent.extras != null){
            id = intent.getIntExtra("id", 0)
            nombre = intent.getStringExtra("nombre")
            precio = intent.getStringExtra("precio")
            userid = intent.getStringExtra("userid")
            email = intent.getStringExtra("email")
            userToken = intent.getStringExtra("userToken")
        }
    }

    fun initUI(){
        btnBuyVideoAlarm.text = "S/ $precio"
        onClickListener()
        culqiPresenter.attachedView(this)

    }

    fun wsCreateToken(){
        val raw = CulqiCreateTokenRaw()
        raw.card_number = eteCardNumber.text.toString()
        raw.cvv = eteCardCvv.text.toString()
        raw.expiration_month = eteCardMonth.text.toString()
        raw.expiration_year = "20${eteCardYear.text.toString()}"
        raw.email =  email
        culqiPresenter.createToken(raw)
    }

    fun chargeCulqi(){
        val raw = CulqiChargeRaw()
        LogUtils().v("MONTO CULQI :::", precio)
        //raw.amount = replaceMonto(precio)
        raw.amount = replaceMonto("3.00")
        raw.currency_code = "PEN"
        raw.email = email

        val metadata = Metadata()
        metadata.id_users = userid
        raw.metadata = metadata
        raw.source_id = ID_SOURCE
 /*     var antifraud = AntifraudDetailsEntity()
        antifraud.address = "tienda"
        antifraud.address_city = "false"
        antifraud.country_code = "PE"
        antifraud.first_name = nombre
        antifraud.last_name = nombre
        antifraud.phone_number = "123456789"
        raw.antifraud_details = antifraud*/
        culqiPresenter.chargeCulqi(raw)

    }

    fun wsCreateOrder(ID_CHARGE: String, status: String) {
        val raw = CreateOrderRaw()
        raw.id_charge = ID_CHARGE
        raw.id_product = productID
        raw.id_status = status
        raw.total = "3.00"
        culqiPresenter.createOrden(raw)
    }

    fun replaceMonto(montoOld: String): String {
        val monto: String = montoOld.replace(".", "")
        return monto
    }

    fun onClickListener(){
        btnBuyVideoAlarm.setOnClickListener {
            if (validateForm()) {
                hideSoftKeyboard(eteCardNumber)
                vLoading.visibility = View.VISIBLE
                wsCreateToken()
            }
        }
    }

    fun validateFormatCard(){
        eteCardNumber.addTextChangedListener(object : TextWatcher {
            internal var size = 0
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
       /*         if (size < s.length) {
                    when (iviLogoCard.tag) {
                        tagVisa, tagMasterCard -> {
                            if (size == 4 || size == 9 || size == 14) {
                                eteCardNum.append(" ")
                            }
                            if (s.length == 19)
                                eteCardMonth.requestFocus()
                        }
                        tagAmex -> {
                            if (size == 4 || size == 11) {
                                eteCardNum.append(" ")
                            }
                            if (s.length == 17)
                                eteCardMonth.requestFocus()
                        }
                        tagDiners -> {
                            if (size == 4 || size == 11) {
                                eteCardNum.append(" ")
                            }
                            if (s.length == 16)
                                eteCardMonth.requestFocus()
                        }
                    }
                }*/
            }
        })
    }

    fun validateForm(): Boolean{
        return when {
            eteCardNumber.text.isEmpty() -> {
                eteCardNumber.error = "Por favor, ingresa número de tarjeta"
                false
            }
            eteCardMonth.text.isEmpty() -> {
                eteCardMonth.error = "Por favor, ingresa mes"
                false
            }
            eteCardYear.text.isEmpty() -> {
                eteCardYear.error = "Por favor, ingresa año"
                false
            }
            eteCardCvv.text.isEmpty() -> {
                eteCardCvv.error = "Por favor, ingresa CVV"
                false
            }
            else -> true
        }
    }

    override fun createToken(culqiCreateToken: CulqiCreateToken) {
        LogUtils().v(TAG, " TOKEN CREADO " + culqiCreateToken.toString())
        if (culqiCreateToken.`object`!! == SUCCESS_CULQI_TOKEN){
            ID_SOURCE = culqiCreateToken.id!!
            chargeCulqi()
        } else {
            vLoading.visibility = View.GONE
        }
    }

    override fun chargeCulqi(culqiCharges: CulqiCharges) {
        LogUtils().v(TAG, " CHARGED CULQI " + culqiCharges.toString())
        LogUtils().v(TAG, " CHARGED CULQI_charge " + culqiCharges.`object`)
        if (culqiCharges.`object` == "charge"){
            wsCreateOrder(culqiCharges.id!!, "1")
        } else {
            vLoading.visibility = View.GONE
        }

    }

    override fun createOrder(createOrder: CreateOrder) {
        LogUtils().v(TAG, " ORDEN CREADA " + createOrder.toString())
        vLoading.visibility = View.GONE
        if (createOrder.order_id != "") {

        } else {

        }
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessageError(message: String, type: Int?) {

    }

    override fun getContext(): Context {
        return this
    }
}