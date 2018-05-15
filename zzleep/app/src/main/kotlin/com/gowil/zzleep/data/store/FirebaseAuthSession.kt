package com.gowil.zzleep.data.store

import com.google.firebase.auth.FirebaseAuth
import com.gowil.zzleep.app.core.utils.LogUtils

class FirebaseAuthSession {

    fun getInstanceCurrentUser():String{
        var user = FirebaseAuth.getInstance().currentUser
        var idToken = "none"
        user!!.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        idToken = task.result.token!!
                        LogUtils().v(" getInstanceCurrentUser", " getIdToken:: " + idToken!!)
                        // Send token to your backend via HTTPS
                        // ...
                    } else {
                        // Handle error -> task.getException();
                        idToken = task.exception.toString()
                    }
                }
        return idToken
    }
}