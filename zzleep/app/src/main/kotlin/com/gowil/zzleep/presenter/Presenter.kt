package com.gowil.zzleep.presenter

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.view.View


interface Presenter<T>{

    fun attachedView(view: T)
    fun removeView()

}