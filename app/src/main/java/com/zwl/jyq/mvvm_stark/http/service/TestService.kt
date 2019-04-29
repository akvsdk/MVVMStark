package com.zwl.jyq.mvvm_stark.http.service

import com.zwl.jyq.mvvm_stark.entity.UpdateBean
import com.zwl.jyq.mvvm_stark.http.service.bean.Respose
import io.reactivex.Flowable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TestService {

    @FormUrlEncoded
    @POST("https://zchat.zwltech.com/interface/sys.ashx")
    fun getUpdate(@FieldMap param: @JvmSuppressWildcards Map<String, Any>): Flowable<Respose<UpdateBean>>
}