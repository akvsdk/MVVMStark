package com.zwl.jyq.mvvm_stark.http

import com.github.qingmei2.core.GlobalErrorTransformer
import com.qingmei2.rhine.ext.toast
import com.zwl.jyq.mvvm_stark.App
import com.zwl.jyq.mvvm_stark.base.Result
import com.zwl.jyq.mvvm_stark.http.service.bean.Respose
import com.zwl.jyq.mvvm_stark.utils.ApiException
import com.zwl.jyq.mvvm_stark.utils.ServerException
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher


const val SUCCESS = 200
const val RELOGIN = 300

fun <T> globalHandleError(): GlobalErrorTransformer<T> = GlobalErrorTransformer(
    globalDoOnErrorConsumer = { error ->
        when (error) {
            is ApiException -> {
                App.INSTANCE.toast("${error.code},${error.displayMessage}")
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
            .map(HandleFuc())
            .doOnSubscribe {

            }
            .doFinally {

            }
            .onErrorResumeNext(HttpResponseFunc<T>())
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

internal class HandleFuc<T> : Function<Respose<T>, T> {
    @Throws(Exception::class)
    override fun apply(result: Respose<T>): T {
        when (result.code) {
            SUCCESS -> if (result.data != null) return result.data else throw Errors.EmptyResultsError
            RELOGIN -> throw Errors.ReLoginError
            else -> throw ServerException(result.message, result.code)
        }
    }
}

internal class HttpResponseFunc<T> : Function<Throwable, Publisher<out T>> {
    @Throws(Exception::class)
    override fun apply(@NonNull throwable: Throwable): Publisher<T> {
        return when (throwable) {
            is Errors -> Flowable.error(throwable)
            else -> Flowable.error(ApiException.handleException(throwable))
        }

    }
}