package com.astrick.sports.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astrick.sports.data.ContentRepository
import com.astrick.sports.data.Resource
import com.astrick.sports.data.Sport
import com.astrick.sports.ui.SportController.SportState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class SportsDetailsViewModel(
    private val sportsRepo: ContentRepository,
    private val sportDetailsController: SportController
) : ViewModel() {
    
    private val _sport = MutableStateFlow<Resource<Sport>>(Resource.Loading)
    val sport: StateFlow<Resource<Sport>> = _sport.asStateFlow()
    
    init {
        viewModelScope.launch {
            sportDetailsController.sportState.collectLatest { sportResource ->
                handleSportResource(sportResource)
            }
        }
    }
    
    private fun handleSportResource(sportState: SportState) {
        if (sportState == SportState.Empty) {
            load()
        } else if (sportState is SportState.Ready) {
            _sport.update { Resource.Ready(sportState.sport) }
        }
    }
    
    private var loadingJob: Job? = null
    private fun load() {
        if (loadingJob?.isActive == true)
            loadingJob?.cancel()
        
        loadingJob = viewModelScope.launch {
            val sports = sportsRepo.getFeaturedSports()
            sportDetailsController.updateSports(sports)
            sportDetailsController.randomize()
        }
    }
    
    fun onDestroy() {
        loadingJob?.cancel()
    }
    
}
