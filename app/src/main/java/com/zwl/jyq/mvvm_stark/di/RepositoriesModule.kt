package com.zwl.jyq.mvvm_stark.di

import com.zwl.jyq.mvvm_stark.repository.UserInfoRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

private const val GLOBAL_REPOS_MODULE_TAG = "PrefsModule"

val globalRepositoryModule = Kodein.Module(GLOBAL_REPOS_MODULE_TAG) {


    bind<UserInfoRepository>() with singleton {
        UserInfoRepository.instance
    }
}