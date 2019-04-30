package com.zwl.jyq.mvvm_stark.ui.test

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

private const val TEST_MODULE_TAG = "TEST_MODULE_TAG"

val testKodeinModule = Kodein.Module(TEST_MODULE_TAG) {

    bind<TestViewModel>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        TestViewModel.instance(context, instance())
    }

    bind<TestDataSourceRepository>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        TestDataSourceRepository(instance(), instance())
    }

    bind<TestLocalDataSource>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        TestLocalDataSource()
    }

    bind<TestRemoteDataSource>() with scoped<AppCompatActivity>(AndroidLifecycleScope).singleton {
        TestRemoteDataSource()
    }
}