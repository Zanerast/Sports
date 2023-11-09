package com.astrick.sports.ui

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import com.astrick.sports.data.Sport
import com.astrick.sports.util.getParcelableArrayListWithClass
import com.astrick.sports.util.getParcelableWithClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

class SportStateController(
    private val id: String,
    private val registryOwner: SavedStateRegistryOwner,
): SportController() {
    
    init {
        setupSavedStateRegistryObserver()
    }
    
    private fun setupSavedStateRegistryObserver() {
        object : SavedStateRegistry.SavedStateProvider {
            
            init {
                registryOwner.lifecycle.addObserver(
                    LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_CREATE) {
                            handleOnCreateState()
                        }
                    }
                )
            }
            
            private fun handleOnCreateState() {
                val registry = registryOwner.savedStateRegistry
                registry.registerSavedStateProvider(id, this)
                val state = registry.consumeRestoredStateForKey(id)
                
                val restoredSport = state?.getParcelableWithClass<Sport>(SPORT_KEY)
                val restoredSports = state?.getParcelableArrayListWithClass<Sport>(SPORTS_KEY)
                
                if (restoredSport != null && restoredSports != null) {
                    restoreSportState(SportState.Ready(restoredSport), restoredSports.toList())
                } else {
                    restoreSportState(SportState.Empty, emptyList())
                }
            }
            
            override fun saveState(): Bundle {
                val bundle = Bundle()
                val resource = sportState.value
                if (resource is SportState.Ready) {
                    bundle.putParcelableArrayList(SPORTS_KEY, ArrayList(sports))
                    bundle.putParcelable(SPORT_KEY, resource.sport)
                }
                return bundle
            }
            
        }
    }
    
}
