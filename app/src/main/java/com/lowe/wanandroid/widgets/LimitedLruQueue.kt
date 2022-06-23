package com.lowe.wanandroid.widgets

import java.util.*

class LimitedLruQueue<E>(private val limit: Int) : LinkedList<E>() {
    override fun add(element: E): Boolean {
        remove(element)
        super.add(element)
        while (size > limit) {
            super.remove()
        }
        return true
    }

    override fun get(index: Int): E {
        check(index in 0 until size) { IndexOutOfBoundsException("Index: $index, Size: $size") }
        return super.get(index).also {
            remove(it)
            add(it)
        }
    }
}