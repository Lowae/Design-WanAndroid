package com.lowe.multitype

sealed interface LoadState {

    object Header : LoadState

    object Footer : LoadState

}