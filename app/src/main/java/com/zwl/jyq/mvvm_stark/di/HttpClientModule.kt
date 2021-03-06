package com.zwl.jyq.mvvm_stark.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.github.simonpercic.oklog3.OkLogInterceptor
import com.lxj.androidktx.okhttp.HttpLogInterceptor
import com.zwl.jyq.mvvm_stark.http.HttpsUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private const val HTTP_CLIENT_MODULE_TAG = "httpClientModule"
const val HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG = "http_client_module_interceptor_log_tag"
const val TIME_OUT_SECONDS = 10
const val BASE_URL = "https://api.github.com/"

val sslParams = HttpsUtils.getSslSocketFactory(null, null, null)

val httpClientModule = Kodein.Module(HTTP_CLIENT_MODULE_TAG) {

    bind<Retrofit.Builder>() with provider { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with provider { OkHttpClient.Builder() }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(BASE_URL)
            .client(instance())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(instance()))
            .build()
    }

    bind<Interceptor>(HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG) with singleton {
        OkLogInterceptor.builder().build()
    }


    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
            .connectTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .readTimeout(
                TIME_OUT_SECONDS.toLong(),
                TimeUnit.SECONDS
            )
            .hostnameVerifier { _, _ -> true }.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
            .addInterceptor(instance(HTTP_CLIENT_MODULE_INTERCEPTOR_LOG_TAG))
            .addNetworkInterceptor(HttpLogInterceptor())
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }
}