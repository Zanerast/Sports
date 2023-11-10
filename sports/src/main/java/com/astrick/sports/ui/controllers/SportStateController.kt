package com.astrick.sports.ui.controllers

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryOwner
import com.astrick.sports.data.Sport
import com.astrick.sports.util.getParcelableArrayListWithClass
import com.astrick.sports.util.getParcelableWithClass

/**
 * This controller is responsible for saving & restoring the state of the [Sport] object
 * when the associated [SavedStateRegistryOwner] is recreated.
 *
 * @param id The id of the [SavedStateRegistry] to be used for saving and restoring state.
 * @param registryOwner The [SavedStateRegistryOwner] to be used for saving and restoring state.
 */
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
