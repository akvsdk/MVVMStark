package com.zwl.jyq.mvvm_stark.http

import com.github.qingmei2.core.GlobalErrorTransformer
import com.qingmei2.rhine.ext.toast
import com.zwl.jyq.mvvm_stark.App
import com.zwl.jyq.mvvm_stark.base.Result
import com.zwl.jyq.mvvm_stark.http.service.bean.Respose
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


const val SUCCESS = 200

fun <T> globalHandleError(): GlobalErrorTransformer<T> = GlobalErrorTransformer(
    globalDoOnErrorConsumer = { error ->
        when (error) {
            is HttpException -> {
                when (error.code()) {
                    401 -> App.INSTANCE.toast { "401 Unauthorized" }
                    404 -> App.INSTANCE.toast { "404 failure" }
                    500 -> App.INSTANCE.toast { "500 server failure" }
                    else -> App.INSTANCE.toast { "network failure" }
                }
            }
            else -> App.INSTANCE.toast { "network failure" }
        }
    }
)


fun <T> handleResults(): FlowableTransformer<Respose<T>, T> {
    return FlowableTransformer { upstream ->
        upstream
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(globalHandleError())
            .map {
                when (SUCCESS) {
                    it.code -> if (it.data != null) it.data else throw Errors.EmptyResultsError
                    else -> throw Errors.EmptyResultsError
                }
            }
    }
}

fun <T> applyAssemble(): FlowableTransformer<T, Result<T>> {
    return FlowableTransformer { upstream ->
        upstream.map {
            Result.success(it)
        }
            .onErrorReturn { Result.failure(it) }
            .startWith(Result.loading())
            .startWith(Result.idle())
    }
}