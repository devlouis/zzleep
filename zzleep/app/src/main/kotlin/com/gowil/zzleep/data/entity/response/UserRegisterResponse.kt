package com.gowil.zzleep.data.entity.response

import com.gowil.zzleep.data.entity.UserRegisterEntity
import java.io.Serializable

/**
 * {
"data": {
"display_name": "Melissa Apolaya",
"email": "melissa_14_80@gmail.com",
"photo_url": null,
"uid": "eybPMOLafIb2G98PI3CCk8vrwXu1"
},
"msg": "Usuario creado exitosamente",
"success": "true"
}
 */
class UserRegisterResponse: Serializable {
    var data: UserRegisterEntity? = null
    var msg: String? = ""
    var success: String? = ""
    override fun toString(): String {
        return "UserRegisterResponse(data=$data, msg=$msg, success=$success)"
    }


}