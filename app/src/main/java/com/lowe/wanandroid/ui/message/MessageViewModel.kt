package com.lowe.wanandroid.ui.message

import com.lowe.wanandroid.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(private val repository: MessageRepository) :
    BaseViewModel() {

    override fun init() {
        super.init()
    }

}