package com.lowe.wanandroid.utils

import android.content.Context
import android.view.Gravity
import android.widget.Toast

/**
 * Toast封装工具类
 * 注：不知咋回事，设置Toast为静态LeakCanary就报内存泄漏，即使设置成context.applicationContext
 */
object ToastUtil {

    /**
     * 显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShort(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示长时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLong(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showShortInCenter(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }

    /**
     * 居中显示短时间的Toast
     *
     * @param context Context
     * @param msg 显示的消息
     */
    fun showLongInCenter(context: Context, msg: String) {
        Toast.makeText(context.applicationContext, msg, Toast.LENGTH_LONG).apply {
            setGravity(Gravity.CENTER, 0, 0)
            show()
        }
    }
}