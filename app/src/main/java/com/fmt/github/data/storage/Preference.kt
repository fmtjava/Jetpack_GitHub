package com.fmt.github.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.fmt.github.AppContext
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * 基于属性代理封装SharedPreferences
 */
class Preference<T>(private val key: String, private val defaultValue: T) :
    ReadWriteProperty<Any?, T> {

    companion object {
        private const val SHARE_PRE_NAME = "open_github"

        private val mPreferences: SharedPreferences by lazy {
            AppContext.getSharedPreferences(
                SHARE_PRE_NAME,
                Context.MODE_PRIVATE
            )
        }

        fun clear() {
            mPreferences.edit().clear()
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        findPreference(key, defaultValue)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        putPreference(key, value)

    private fun findPreference(key: String, defaultValue: T): T = with(mPreferences) {
        when (defaultValue) {
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            is String -> getString(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            else -> throw IllegalArgumentException("This type can't be saved into SharedPreferences")
        } as T
    }


    private fun putPreference(key: String, value: T) {
        with(mPreferences.edit()) {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Boolean -> putBoolean(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("This type can't be saved into SharedPreferences")
            }
        }.apply()
    }
}