package com.fmt.github.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle

//reified修饰类型后，我们就能够在函数内部使用相关类型了
//重要的是，使用内联函数后，在其运行地方需要替换代码来获得类型。事实上由于Java虚拟机的限制，类型不能使用，而是跳过限制的“诡计”。
inline fun <reified T : Activity> Activity.startActivity(isFinish:Boolean = true) {
    startActivity(Intent(this, T::class.java))
    isFinish.yes {
        finish()
    }
}

inline fun <reified T : Activity> Activity.startActivity(extras: Bundle) {
    startActivity(Intent(this, T::class.java).putExtras(extras))
}