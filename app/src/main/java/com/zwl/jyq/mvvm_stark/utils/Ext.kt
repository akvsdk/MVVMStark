package com.zwl.jyq.mvvm_stark.utils

import com.lxj.androidktx.core.md5
import com.zwl.jyq.mvvm_stark.BuildConfig

/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
/**
 *  封装固定传参封装
 */
fun createMap(vararg pairs: Pair<String, Any>): MutableMap<String, Any> {
    val map = mutableMapOf<String, Any>()
    map["appver"] = BuildConfig.VERSION_NAME + ""
    map["sysver"] = "android," + android.os.Build.VERSION.RELEASE
    map.putAll(pairs.toMap())
    var sign = ""
    val temp = map.filter { it.key != "action" }.toSortedMap()
    for ((key, vaule) in temp) {
        sign += "$key=$vaule&"
    }
    sign = sign.substring(0, sign.length - 1)
    map["sign"] = "zwltech:$sign".trim().md5()
    return map
}
