package com.gowil.zzleep.domain.repository.interactor

interface RequestCallBack {
    fun onRequestSuccess(any: Any)
    fun onRequestSuccess(any: Any, type: Int)
    fun onRequestFailure(e: Throwable)
    fun onRequestFailure(throwable: Throwable, type: Int)
}
