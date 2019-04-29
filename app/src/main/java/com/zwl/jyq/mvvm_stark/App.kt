package com.zwl.jyq.mvvm_stark

import android.content.Context
import com.facebook.stetho.Stetho
import com.j1ang.mvvm.BaseApp
import com.j1ang.mvvm.storage.SpUtils
import com.safframework.log.L
import com.zwl.jyq.mvvm_stark.di.globalRepositoryModule
import com.zwl.jyq.mvvm_stark.di.httpClientModule
import com.zwl.jyq.mvvm_stark.di.serializableModule
import com.zwl.jyq.mvvm_stark.di.serviceModule
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
    }

    companion object {
        lateinit var INSTANCE: App
    }
}