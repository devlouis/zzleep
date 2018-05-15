package com.gowil.zzleep.repository

interface RepositoryCallBack {
    fun onSuccess(any: Any)
    fun onSuccess(any: Any, header: Any)
    fun onFailure(throwable: Throwable)
}
