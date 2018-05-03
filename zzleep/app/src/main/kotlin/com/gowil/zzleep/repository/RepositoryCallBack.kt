package com.gowil.zzleep.repository

interface RepositoryCallBack {
    fun onSuccess(obj: Any)
    fun onSuccess(obj: Any, header: Any)
    fun onFailure(throwable: Throwable)
}
