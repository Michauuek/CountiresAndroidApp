package com.michau.countries.ui.level

sealed class LevelScreenEvent {
    data class OnLevelClick(val level: Levels): LevelScreenEvent()
}
