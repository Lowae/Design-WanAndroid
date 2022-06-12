package com.lowe.wanandroid.ui.profile

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import com.lowe.wanandroid.R
import com.lowe.wanandroid.databinding.FragmentProfileBinding
import com.lowe.wanandroid.ui.BaseFragment

val colors = mutableListOf(
    Color.BLACK, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.WHITE,
    Color.RED,
    Color.GREEN,
    Color.BLUE,
    Color.YELLOW,
    Color.CYAN,
    Color.MAGENTA
).apply {
    this + this.reverse()
}.toIntArray()

class ProfileFragment :
    BaseFragment<ProfileViewModel, FragmentProfileBinding>(R.layout.fragment_profile) {


    override fun createViewModel() = ProfileViewModel()

    override fun init(savedInstanceState: Bundle?) {

        getArgbAnimator {
            viewBinding.funny.drawable.colorFilter =
                PorterDuffColorFilter(it, PorterDuff.Mode.SRC_IN)
        }
    }

    fun getArgbAnimator(update: (Int) -> Unit) {
        ObjectAnimator.ofArgb(*colors).apply {
            setDuration(3000)
            addUpdateListener {
                val color = it.animatedValue as Int
                Log.d("getArgbAnimator", "$color")
                update(color)
            }
            interpolator = LinearInterpolator()
            addListener(onEnd = {
                getArgbAnimator(update)
            })
        }.start()
    }
}
