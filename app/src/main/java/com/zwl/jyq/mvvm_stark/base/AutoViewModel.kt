package com.zwl.jyq.mvvm_stark.base

import androidx.lifecycle.MutableLiveData
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.j1ang.mvvm.base.viewmodel.AutoDisposeViewModel
import com.j1ang.mvvm.ext.arrow.whenNotNull
import com.j1ang.mvvm.ext.livedata.toReactiveStream
import com.qingmei2.rhine.ext.toast
import com.uber.autodispose.autoDisposable
import com.zwl.jyq.mvvm_stark.App
import com.zwl.jyq.mvvm_stark.http.Errors
import retrofit2.HttpException

/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
open class AutoViewModel : AutoDisposeViewModel() {

    internal val error: MutableLiveData<Option<Throwable>> = MutableLiveData()


    init {
        error.toReactiveStream()
            .map { errorOpt ->
                errorOpt.flatMap {
                    when (it) {
                        is Errors.EmptyInputError -> "输入不能为空".some()
                        is Errors.EmptyResultsError -> "服务器取回数据为空".some()
                        is HttpException -> "服务器异常".some()
                        else -> none()
                    }
                }
            }
            .autoDisposable(this)
            .subscribe { errorMsg ->
                errorMsg.whenNotNull {
                    App.INSTANCE.toast { it }
                }
            }
    }

}
