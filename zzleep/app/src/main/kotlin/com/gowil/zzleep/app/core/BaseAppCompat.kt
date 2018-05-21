package com.gowil.zzleep.app.core

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.gowil.zzleep.R
import android.view.Gravity
import android.R.attr.gravity
import android.widget.FrameLayout




open class BaseAppCompat: AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun nextActivity(activity: Class<*>) {
        this.nextActivity(activity, false)
    }

    protected fun nextData(activity: Class<*>, bundle: Bundle) {
        this.nextData(activity, bundle, false)
    }

    fun nextActivity(activity: Class<*>, notDestroy: Boolean) {
        this.startActivity(Intent(this, activity))
        if (!notDestroy) {
            this.finish()
        }

    }

    protected fun resultActivity(activity: Class<*>, code: Int) {
        this.startActivityForResult(Intent(this, activity), code)
    }

    protected fun resultActivityData(activity: Class<*>, code: Int, bundle: Bundle) {
        val intent = Intent(this, activity)
        intent.putExtras(bundle)
        this.startActivityForResult(intent, code)
    }

    fun nextData(activity: Class<*>, bundle: Bundle, notDestroy: Boolean) {
        val intent = Intent(this, activity)
        intent.putExtras(bundle)
        this.startActivity(intent)
        if (!notDestroy) {
            this.finish()
        }

    }

    fun snackBar(msj: String, rlaContent: View) {
        val snackbar = Snackbar
                .make(rlaContent, msj, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.color_error))
        snackbar.show()

    }

    fun snackBarFail(msj: String, rlaContent: View) {


        val snackbar = Snackbar
                .make(rlaContent, msj, Snackbar.LENGTH_LONG)
        val view = snackbar.getView()
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.color_error))
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        snackbar.show()

    }

    fun snackBarSucceso(msj: String, rlaContent: View) {
        val snackbar = Snackbar
                .make(rlaContent, msj, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.new_verder))
        snackbar.show()

    }

    fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }

    fun showSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}