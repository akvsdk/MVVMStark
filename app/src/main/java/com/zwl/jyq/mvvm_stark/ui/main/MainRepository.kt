package com.zwl.jyq.mvvm_stark.ui.main

import arrow.core.Either
import com.j1ang.mvvm.base.repository.BaseRepositoryBoth
import com.j1ang.mvvm.base.repository.ILocalDataSource
import com.j1ang.mvvm.base.repository.IRemoteDataSource
import com.j1ang.mvvm.storage.SpUtils
import com.safframework.log.L
import com.zwl.jyq.mvvm_stark.entity.UpdateBean
import com.zwl.jyq.mvvm_stark.entity.UserInfo
import com.zwl.jyq.mvvm_stark.http.Errors
import com.zwl.jyq.mvvm_stark.http.SUCCESS
import com.zwl.jyq.mvvm_stark.http.globalHandleError
import com.zwl.jyq.mvvm_stark.http.handleResults
import com.zwl.jyq.mvvm_stark.http.service.ServiceManager
import com.zwl.jyq.mvvm_stark.manager.UserManager
import com.zwl.jyq.mvvm_stark.repository.UserInfoRepository
import com.zwl.jyq.mvvm_stark.utils.createMap
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.random.Random

/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
class MainRepository(remoteDataSource: MainRemoteDataSource, localDataSource: MainLocalDataSource) :
    BaseRepositoryBoth<MainRemoteDataSource, MainLocalDataSource>(remoteDataSource, localDataSource) {
    fun getDatas(action: String): Flowable<Either<Errors, UpdateBean>> {
        return localDataSource.saveSP("akvsdk", 147258)
            .andThen(remoteDataSource.getUpdate(action))
            .doOnError { localDataSource.clear() }
            .doFinally { localDataSource.showSP() }
    }


    fun showSP() {
        localDataSource.showSP()
    }
}


class MainRemoteDataSource(private val serviceManager: ServiceManager) : IRemoteDataSource {
    val map = createMap("action" to "appconfig")

    fun getUpdates(action: String): Flowable<UpdateBean> {
        L.d(action)
        return serviceManager.testService.getUpdate(map)
            .compose(handleResults())
    }

    fun getUpdate(action: String): Flowable<Either<Errors, UpdateBean>> {
        L.d(action)
        return serviceManager.testService.getUpdate(map)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(globalHandleError())
            .map {
                when (SUCCESS) {
                    it.code -> if (it.data != null) {
                        UserManager.INSTANCE = it.data
                        Either.right(it.data)
                    } else Either.left(Errors.EmptyResultsError)
                    else -> Either.left(Errors.EmptyResultsError)
                }
            }
    }

}


class MainLocalDataSource(private val userRepository: UserInfoRepository) : ILocalDataSource {

    fun saveSP(username: String, password: Int): Completable {
        return Completable.fromAction {
            SpUtils.saveValue("user_access_token", "1111111")
            SpUtils.saveValue("username", username)
            SpUtils.saveValue("password", password)
            SpUtils.saveValue("user", UserInfo(15, "jack", Random.nextInt(0, 99)))

        }
    }

    fun showSP() {
        L.e(userRepository.username)
        L.e("{${userRepository.password}}")
        //   L.e(SpUtils.getObj("user", UserInfo::class.java).toString())
        L.e(SpUtils.getObj("user", UserInfo::class.java).toString())
    }

    fun clear(): Completable {
        return Completable.fromAction {
            SpUtils.clear()
        }

    }

}
