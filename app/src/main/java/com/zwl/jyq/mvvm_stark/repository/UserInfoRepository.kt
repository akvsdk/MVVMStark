package com.zwl.jyq.mvvm_stark.repository

import com.j1ang.mvvm.storage.SpUtils
import com.zwl.jyq.mvvm_stark.entity.UserInfo


/**
 * 全局赋值一次单，启动后数据源不可变
 */
class UserInfoRepository private constructor() {
    companion object {
        val instance: UserInfoRepository by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            UserInfoRepository()
        }
    }

    var accessToken: String = SpUtils.getValue("user_access_token", "")
    var username: String = SpUtils.getValue("username", "")
    var password: Int = SpUtils.getValue("password", 0)
    var user = SpUtils.getObj("user", UserInfo::class.java)

}

