package com.fmt.github.ext

import android.widget.Toast
import com.fmt.github.App
import es.dmoral.toasty.Toasty

fun successToast(error: String) {
    Toasty.success(App.mApplication, error, Toast.LENGTH_SHORT, true).show()
}

fun errorToast(error: String) {
    Toasty.error(App.mApplication, error, Toast.LENGTH_SHORT, true).show();
}
