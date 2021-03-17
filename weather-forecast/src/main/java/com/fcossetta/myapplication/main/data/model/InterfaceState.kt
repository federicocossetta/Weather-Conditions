package com.fcossetta.myapplication.main.data.model

import io.uniflow.core.flow.data.UIState

open class InterfaceState : UIState() {
    data class LoadingStatus(val loading: Boolean) : InterfaceState()

}