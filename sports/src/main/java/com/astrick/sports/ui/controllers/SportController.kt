package com.astrick.sports.ui.controllers

import com.astrick.sports.data.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Controller class responsible for managing the state and actions related to sports details.
 *
 * This class maintains a list of sports and provides methods for updating,
 * randomizing, and restoring the sports state.
 */
open class SportController {
    internal val sports = mutableListOf<Sport>()
    private val _sportState = MutableStateFlow<SportState>(SportState.Loading)
    internal val sportState: StateFlow<SportState> = _sportState.asStateFlow()
    
    internal fun updateSports(sports: List<Sport>) {
        this.sports.clear()
        this.sports.addAll(sports)
    }
    
    fun randomize() {
        val currentState = sportState.value
        var randomSport = sports.random()
        if (currentState is SportState.Ready) {
            while (currentState.sport == randomSport) {
                randomSport = sports.random()
            }
        }
        _sportState.update { SportState.Ready(randomSport) }
    }
    
    internal fun restoreSportState(resource: SportState, sports: List<Sport>) {
        updateSports(sports)
        _sportState.update { resource }
    }
    
    internal sealed class SportState {
        object Loading : SportState()
        object Empty : SportState()
        data class Ready(val sport: Sport) : SportState()
    }
    
    companion object {
        internal const val SPORT_KEY = "sport"
        internal const val SPORTS_KEY = "sports"
    }
}
