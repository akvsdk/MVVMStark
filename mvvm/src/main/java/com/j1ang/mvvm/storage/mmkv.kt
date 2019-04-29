package com.j1ang.mvvm.storage

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.os.Parcelable
import com.safframework.log.L
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVHandler
import com.tencent.mmkv.MMKVLogLevel
import com.tencent.mmkv.MMKVRecoverStrategic
import java.io.File
import java.io.IOException

/**
 * Date:  2019/4/28.
 * version:  V1.0
 * Description:
 * @author Joy
 */
@SuppressLint("StaticFieldLeak")
object SpUtils : MMKVHandler {

    private var CONFIG_NAME = "MmkvStark"
    private lateinit var context: Context
    private var MODE = MMKV.SINGLE_PROCESS_MODE
    lateinit var prefs: MMKV


    fun init(context: Context, configName: String = CONFIG_NAME, mode: Int = MODE) {
        this.context = context
        MMKV.initialize(SpUtils.context)
        MMKV.setLogLevel(MMKVLogLevel.LevelInfo)
        MMKV.registerHandler(this)
        prefs = MMKV.defaultMMKV()
    }

    fun save(key: String, value: Any) {
        prefs.let {
            when (value) {
                is Long -> it.encode(key, value)
                is Int -> it.encode(key, value)
                is String -> it.encode(key, value)
                is Float -> it.encode(key, value)
                is Boolean -> it.encode(key, value)
                is ByteArray -> it.encode(key, value)
                is Parcelable -> it.encode(key, value)
                else -> throw  IllegalArgumentException("MMKV 类型错误")
            }
        }
    }

    inline fun <reified T> get(key: String, def: T? = null): T {
        with(prefs) {
            return when (T::class) {
                Int::class -> decodeInt(key, if (def is Int) def else 0)
                String::class -> decodeString(key, if (def is String) def else "")
                Long::class -> decodeLong(key, if (def is Long) def else 0L)
                Float::class -> decodeFloat(key, if (def is Float) def else 0.0f)
                Boolean::class -> decodeBool(key, if (def is Boolean) def else false)
                ByteArray::class -> decodeBytes(key)
                else -> throw IllegalArgumentException("MVVK 类型错误")
            } as T
        }
    }

    fun clear() {
        prefs.edit().clear().apply()
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }


    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    fun saveValue(key: String, value: Any) =
        save(key = key, value = value)

    inline fun <reified T> getValue(key: String, def: T? = null) =
        get(key = key, def = def)

    fun <T : Parcelable> getObj(key: String, clzz: Class<T>): T? {
        return if (prefs.decodeParcelable(key, clzz) == null)  null else prefs.decodeParcelable(key, clzz)
     }

    /**
     * 获取全局下载地址
     *
     * @return
     */
    @Throws(IOException::class)
    fun getLocalPath(): String {
        val sdCardExist = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED // 判断sd卡是否存在
        if (sdCardExist) {
            val path = File(
                Environment.getExternalStorageDirectory()
                    .absolutePath + "/{$CONFIG_NAME}"
            )
            if (path.exists() && !path.isDirectory) {
                path.delete()
            }
            path.mkdirs()

            return Environment.getExternalStorageDirectory().absolutePath + "/{$CONFIG_NAME}"
        } else {
            throw IOException()
        }
    }


    override fun onMMKVCRCCheckFail(mmapID: String?): MMKVRecoverStrategic {
        return MMKVRecoverStrategic.OnErrorRecover
    }

    override fun wantLogRedirecting(): Boolean {
        return true
    }

    override fun mmkvLog(level: MMKVLogLevel?, file: String?, line: Int, func: String?, message: String?) {
        val log = "<$file:$line::$func> $message"
        when (level) {
            MMKVLogLevel.LevelDebug -> L.d(log)
            MMKVLogLevel.LevelInfo -> L.i(log)
            MMKVLogLevel.LevelWarning -> L.w(log)
            MMKVLogLevel.LevelError -> L.e(log)
            MMKVLogLevel.LevelNone -> L.e(log)
            else -> L.e(log)
        }
    }

    override fun onMMKVFileLengthError(mmapID: String?): MMKVRecoverStrategic {
        return MMKVRecoverStrategic.OnErrorRecover
    }

}