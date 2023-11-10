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
    
    internal fun onSportsLoaded(sports: List<Sport>) {
        updateList(sports)
        if (sports.isNotEmpty()) {
            val randomSport = sports.random()
            _sportState.update { SportState.Ready(randomSport) }
        }
    }
    
    fun randomizeNextSport() {
        val currentState = sportState.value
        if (currentState is SportState.Ready) {
            var randomSport = sports.random()
            while (currentState.sport == randomSport) {
                randomSport = sports.random()
            }
            _sportState.update { SportState.Ready(randomSport) }
        }
    }
    
    internal fun restoreSportState(resource: SportState, sports: List<Sport>) {
        updateList(sports)
        _sportState.update { resource }
    }
    
    private fun updateList(sports: List<Sport>) {
        this.sports.clear()
        this.sports.addAll(sports)
    }
    
    internal sealed class SportState {
        object Loading : SportState()
        object Empty : SportState()
        data class Ready(val sport: Sport) : SportState()
    }

}
