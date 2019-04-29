package com.j1ang.mvvm

import android.app.Application


/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
open class BaseApp : Application() {

    private lateinit var BaseApp: BaseApp

    override fun onCreate() {
        super.onCreate()
        BaseApp = this
    }


}