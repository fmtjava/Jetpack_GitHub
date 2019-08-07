package com.fmt.github.ext

//Boolean扩展 再见了if else

sealed class BooleanExt<out T>//巧用协变与密封类(增强版枚举)

class Success<T>(val data: T) : BooleanExt<T>()

object OtherWise : BooleanExt<Nothing>()

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> =//inline提升性能
    when {
        this -> {
            Success(block())
        }
        else -> {
            OtherWise
        }
    }

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Success -> this.data
        OtherWise -> block()
    }
