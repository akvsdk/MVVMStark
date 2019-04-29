package com.zwl.jyq.mvvm_stark.ui.main

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"


val mainModule = Kodein.Module(MAIN_MODULE_TAG) {
    bind<MainViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        MainViewModel.instance(
            activity = context,
            repo = instance()
        )
    }

    bind<MainRemoteDataSource>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        MainRemoteDataSource(serviceManager = instance())
    }

    bind<MainLocalDataSource>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        MainLocalDataSource(userRepository = instance())
    }

    bind<MainRepository>() with singleton {
        MainRepository(instance(), instance())
    }

}