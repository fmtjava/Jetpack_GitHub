package com.fmt.github.ext

//Boolean扩展 再见了if else

sealed class BooleanExt<out T>//巧用协变与密封类(增强版枚举)

class Success<T>(val data: T) : BooleanExt<T>()//data是协变点(get方法)

object OtherWise : BooleanExt<Nothing>()//Nothing是任何类的子类

inline fun <T> Boolean.yes(block: () -> T): BooleanExt<T> =//inline提升性能
    when {//类似于if-else if - else链,不提供参数，所有的分支条件都是简单的布尔表达式，而当一个分支的条件为真时则执行该分支
        this -> Success(block())
        else -> OtherWise
    }

fun <T> Boolean.no(block: () -> T): BooleanExt<T> = when {
    this -> OtherWise
    else -> Success(block())
}

inline fun <T> BooleanExt<T>.otherwise(block: () -> T): T =
    when (this) {
        is Success -> this.data
        OtherWise -> block()
    }

