package com.zwl.jyq.mvvm_stark.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.lxj.androidktx.core.md5
import com.zwl.jyq.mvvm_stark.BuildConfig
import com.zwl.jyq.mvvm_stark.R
import io.reactivex.ObservableTransformer

/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */


fun Int.toColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

fun Int.toDrawable(context: Context): Drawable {
    return ContextCompat.getDrawable(context, this)!!
}

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


/**
 * 自定义loading
 */
fun loadingDialog(context: AppCompatActivity, loadingText: String? = null): Dialog {
    return MaterialDialog(context).show {
        cancelable(true)
        message(null, loadingText ?: context.getString(R.string.load))
        cancelOnTouchOutside(true)
        customView(R.layout.ui_dialog_loading)
        lifecycleOwner(context)
    }

}

fun Dialog?.safeDismiss() {
    if (this != null && isShowing) {
        dismiss()
    }
}

/**
 *Rx请求是添加Loading
 */

fun <T> rxDialog(context: AppCompatActivity?): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        if (context != null) {
            val dialog = loadingDialog(context)
            observable
                .doOnSubscribe { dialog.show() }
                .doOnTerminate { dialog.safeDismiss() }
                .doOnDispose { dialog.safeDismiss() }
        } else observable
    }
}

/**
 * 显示AlertDialog
 * @param positiveStr 如果为空则不显示
 * @param negativeStr 如果为空则不显示
 */
fun Activity.showAlertDialog(
    context: AppCompatActivity,
    title: String,
    msg: String,
    confirmListener: ((ok: Boolean) -> Unit)?
) {
    MaterialDialog(context).show {
        title(null, title)
        message(null, msg)
        positiveButton(null, "确认") {
            confirmListener?.invoke(true)
        }
        negativeButton(null, "取消") {
            confirmListener?.invoke(false)
        }
        lifecycleOwner(context)
    }
}




