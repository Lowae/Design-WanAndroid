package com.lowe.wanandroid.utils

import android.view.Gravity
import android.widget.Toast
import com.lowe.wanandroid.base.app.BaseApp

/**
 * Toast封装工具类
 * 注：不知咋回事，设置Toast为静态LeakCanary就报内存泄漏，即使设置成context.applicationContext
 */
object ToastEx {

    /**
     * 显示短时间的Toast
     */
    fun String.showShortToast() =
        Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_SHORT).show()

    /**
     * 显示长时间的Toast
     */
    fun String.showLongToast() = Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_LONG).show()

    /**
     * 居中显示短时间的Toast
     */
    fun String.showShortToastInCenter() {
        Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    /**
     * 居中显示短时间的Toast
     */
    fun String.showLongToastInCenter() {
        Toast.makeText(BaseApp.appContext, this, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}