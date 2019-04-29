package com.zwl.jyq.mvvm_stark.di

import com.zwl.jyq.mvvm_stark.http.service.ServiceManager
import com.zwl.jyq.mvvm_stark.http.service.TestService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

private const val SERVICE_MODULE_TAG = "serviceModule"

val serviceModule = Kodein.Module(SERVICE_MODULE_TAG) {


    bind<TestService>() with singleton {
        instance<Retrofit>().create(TestService::class.java)
    }


    bind<ServiceManager>() with singleton {
        ServiceManager(instance())
    }

}