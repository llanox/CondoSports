package com.gabo.condorsports.presentation

sealed class ScreenState<out T> (
    val data: T? = null,
    val message: String? = null,
    val action: Action? = null
){
    class Loading : ScreenState<Nothing>()
    class Render<T>(data: T, action: Action? = null) : ScreenState<T>(data = data, action = action)
    class Error<T>( message: String, data: T? = null) : ScreenState<T>(data, message)
}

enum class Action { RENDER_TEAM_EVENTS, RENDER_TEAMS, RENDER_TEAM_DETAILS, RENDER_LEAGUES }

