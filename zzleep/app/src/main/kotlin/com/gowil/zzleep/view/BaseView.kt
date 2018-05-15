package com.gowil.zzleep.view

import android.content.Context

interface BaseView {
    fun getContext(): Context
    fun showMessageError(message: String, type: Int?)
}