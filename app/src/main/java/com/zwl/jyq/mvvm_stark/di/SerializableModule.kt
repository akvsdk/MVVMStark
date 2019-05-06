package com.zwl.jyq.mvvm_stark.di

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import java.io.IOException

private const val SERIALIZABLE_MODULE_TAG = "SerializableModule"

val serializableModule = Kodein.Module(SERIALIZABLE_MODULE_TAG) {

    bind<Gson>() with singleton {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .serializeNulls()
            .registerTypeAdapterFactory(SafeTypeAdapterFactory()).create()
    }
}

internal class SafeTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any?> create(gson: Gson?, type: TypeToken<T>?): TypeAdapter<T> {
        val delegate = gson?.getDelegateAdapter<T>(this, type)

        return object : TypeAdapter<T>() {
            override fun write(out: JsonWriter?, value: T) {
                try {
                    delegate?.write(out, value)
                } catch (e: IOException) {
                    delegate?.write(out, null)
                }

            }

            override fun read(`in`: JsonReader?): T? {
                try {
                    return delegate?.read(`in`)
                } catch (e: IOException) {
                    `in`?.skipValue()
                    return null
                } catch (e: IllegalStateException) {
                    `in`?.skipValue()
                    return null
                } catch (e: JsonSyntaxException) {
                    `in`?.skipValue()
                    if (type?.type is Class<*>) {
                        try {
                            return (type.type as Class<*>).newInstance() as T
                        } catch (e1: Exception) {

                        }

                    }
                    return null
                }
            }

        }
    }

}

