package com.lowe.wanandroid.ui

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.lowe.wanandroid.databinding.FragmentHomeChildAnswerBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ActivityDataBindingDelegate<out VD : ViewDataBinding>(@LayoutRes private val layoutRes: Int) :
    ReadOnlyProperty<Activity, VD> {

    private var binding: VD? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): VD =
        binding ?: DataBindingUtil.setContentView<VD>(thisRef, layoutRes)
            .also { binding = it }

}