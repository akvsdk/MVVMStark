package com.zwl.jyq.mvvm_stark.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.j1ang.mvvm.utils.SingletonHolderSingleArg
import com.lxj.androidktx.core.logd
import com.uber.autodispose.autoDisposable
import com.zwl.jyq.mvvm_stark.base.AutoViewModel
import com.zwl.jyq.mvvm_stark.base.Result
import com.zwl.jyq.mvvm_stark.entity.UpdateBean
import com.zwl.jyq.mvvm_stark.http.Errors
import io.reactivex.Single


/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
class MainViewModel(private val repo: MainRepository) : AutoViewModel() {
    val result: MutableLiveData<UpdateBean> = MutableLiveData()
    val timeToShow: MutableLiveData<Boolean> = MutableLiveData()

    companion object {
        fun instance(activity: AppCompatActivity, repo: MainRepository): MainViewModel =
            ViewModelProviders.of(activity, MainViewModelFactory.getInstance(repo)).get(MainViewModel::class.java)
    }


    fun getUpdateInfo(txt: String) {
        when (txt.isNullOrEmpty()) {
            true -> applyState(error = Errors.EmptyInputError.some())
            false -> repo.getDatas(txt).map { either ->
                either.fold({
                    Result.failure<UpdateBean>(it)
                }, {
                    Result.success(it)
                })
            }
                .startWith(Result.loading())
                .startWith(Result.idle())
                .onErrorReturn { Result.failure(it) }
                .autoDisposable(this)
                .subscribe { state ->
                    when (state) {
                        is Result.Loading -> applyState(timeTo = true)
                        is Result.Idle -> applyState(timeTo = false)
                        is Result.Failure -> applyState(error = state.error.some(), timeTo = false)
                        is Result.Success -> applyState(user = state.data.some(), timeTo = false)
                    }
                }
        }
    }

    fun showSp() {
        repo.showSP()
        Single.fromCallable { 123 }.map {
            "456"
        }.map {
            0xff
        }
            .autoDisposable(this)
            .subscribe({ s ->
                logd("$s")
            }, { e -> })
    }

    private fun applyState(
        user: Option<UpdateBean> = none(),
        error: Option<Throwable> = none(),
        timeTo: Boolean? = null
    ) {
        this.error.postValue(error)
        this.result.postValue(user.orNull())
        timeTo.apply(timeToShow::postValue)

    }

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repo: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(repo) as T

    companion object : SingletonHolderSingleArg<MainViewModelFactory, MainRepository>(::MainViewModelFactory)
}