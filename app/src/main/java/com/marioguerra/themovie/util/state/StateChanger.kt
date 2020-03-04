package com.marioguerra.themovie.util.state

data class StateChanger<S>(
    val state: S,
    val sideEffect: SideEffect = SideEffect.empty
)