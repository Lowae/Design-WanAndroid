package com.lowe.wanandroid.utils

import kotlin.random.Random

object ColorUtil {


    fun getRandomWarmColor() =
        hsvToRgb(Random.nextDouble(0.0, 360.0).toFloat(), 1f, 1f)

    fun getRandomColor() = rgb(
        Random.nextInt(0, 255).toFloat(),
        Random.nextInt(0, 255).toFloat(),
        Random.nextInt(0, 255).toFloat()
    )

    fun hsvToRgb(hue: Float, saturation: Float, value: Float): Int {
        val h = (hue % 6).toInt()
        val f = hue / 60 - h
        val p = value * (1 - saturation)
        val q = value * (1 - f * saturation)
        val t = value * (1 - (1 - f) * saturation)
        return when (h) {
            0 -> rgb(value, t, p)
            1 -> rgb(q, value, p)
            2 -> rgb(p, value, t)
            3 -> rgb(p, q, value)
            4 -> rgb(t, p, value)
            5 -> rgb(value, p, q)
            else -> rgb(0f, 0f, 0f)
        }
    }

    fun rgb(red: Float, green: Float, blue: Float) = argb(1F, red, green, blue)

    fun argb(alpha: Float, red: Float, green: Float, blue: Float) =
        (alpha * 255.0f + 0.5f).toInt() shl 24 or ((red * 255.0f + 0.5f).toInt() shl 16) or ((green * 255.0f + 0.5f).toInt() shl 8) or (blue * 255.0f + 0.5f).toInt()
}