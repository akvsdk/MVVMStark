package com.zwl.jyq.mvvm_stark.utils

import com.google.gson.Gson
import com.zwl.jyq.mvvm_stark.App
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

object GsonUtils : KodeinAware {

    override val kodein: Kodein
        get() = App.INSTANCE.kodein

    val INSTANCE: Gson by instance()
}

fun <T> T.toJson(): String {
    return GsonUtils.INSTANCE.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return GsonUtils.INSTANCE.fromJson(this, T::class.java)
}