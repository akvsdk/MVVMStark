package com.qingmei2.rhine.ext

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Toast


/************************************************************************************************
 *
 * Context 系统相关
 *
 **********************************************************************************************/
/**
 * 跳转浏览器
 */
fun Context.jumpBrowser(url: String) {
    val uri = Uri.parse(url)
    Intent(Intent.ACTION_VIEW, uri).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(this)
    }
}

/**
 * 通用toast
 */
fun Context.toast(value: String) = toast { value }

inline fun Context.toast(value: () -> String) =
    Toast.makeText(this, value(), Toast.LENGTH_SHORT).show()

/**
 * context
 */
fun Context._toast(msg: CharSequence) = toast(context = this, msg = msg, time = Toast.LENGTH_SHORT)


fun Context._long_toast(msg: CharSequence) = toast(context = this, msg = msg, time = Toast.LENGTH_LONG)

fun toast(context: Context, msg: CharSequence, time: Int) {
    Toast.makeText(context.applicationContext, msg, time).show()
}


/************************************************************************************************
 *
 * View相关
 *
 **********************************************************************************************/

/**
 * 点击事件扩展方法
 */
fun View.onClick(method: () -> Unit): View {
    setOnClickListener { method.invoke() }
    return this
}


/**
 * 设置View的可见
 */
fun View.visible(isVisible: Boolean): View {
    visibility = if (isVisible) View.VISIBLE else View.GONE
    return this
}

/************************************************************************************************
 *
 * 系统API相关
 *
 **********************************************************************************************/

/**
 * 通过uri  获取文件的路径
 */
fun Uri.getRealFilePath(context: Context): String? {
    val scheme = this.scheme
    var data: String? = null
    if (scheme == null)
        data = this.path
    else if (ContentResolver.SCHEME_FILE == scheme) {
        data = this.path
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = context.contentResolver.query(this, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
        if (null != cursor) {
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                if (index > -1) {
                    data = cursor.getString(index)
                }
            }
            cursor.close()
        }
    }
    return data
}


