package com.zwl.jyq.mvvm_stark

import android.content.Context
import com.facebook.stetho.Stetho
import com.j1ang.mvvm.BaseApp
import com.j1ang.mvvm.crash.CaocConfig
import com.j1ang.mvvm.storage.SpUtils
import com.lxj.androidktx.AndroidKtxConfig
import com.safframework.log.L
import com.zwl.jyq.mvvm_stark.di.globalRepositoryModule
import com.zwl.jyq.mvvm_stark.di.httpClientModule
import com.zwl.jyq.mvvm_stark.di.serializableModule
import com.zwl.jyq.mvvm_stark.di.serviceModule
import com.zwl.jyq.mvvm_stark.ui.main.MainActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
open class App : BaseApp(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        bind<Context>() with singleton {
            this@App
        }
        import(androidModule(this@App))
        import(androidXModule(this@App))

        import(serviceModule)
        // import(dbModule)
        import(httpClientModule)
        import(serializableModule)
        import(globalRepositoryModule)

    }


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        L.init("J1ang")
        SpUtils.init(this)
        Stetho.initializeWithDefaults(this)
        AndroidKtxConfig.init(
            context = this,
            isDebug = BuildConfig.DEBUG,
            defaultLogTag = "J1ang",
            sharedPrefName = "spName"
        )
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
            .enabled(true) //是否启动全局异常捕获
            .showErrorDetails(true) //是否显示错误详细信息
            .showRestartButton(true) //是否显示重启按钮
            .trackActivities(true) //是否跟踪Activity
            .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
            .errorDrawable(R.mipmap.ic_launcher) //错误图标
            .restartActivity(MainActivity::class.java) //重新启动后的activity
            //                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
            //                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
            .apply()
    }

    companion object {
        lateinit var INSTANCE: App
    }
}