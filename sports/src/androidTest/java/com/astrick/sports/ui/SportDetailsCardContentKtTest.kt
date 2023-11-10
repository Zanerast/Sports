package com.astrick.sports.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.astrick.sports.R
import com.astrick.sports.data.Resource
import com.astrick.sports.data.Sport
import com.astrick.sports.ui.util.onNodeWithTextForStringId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SportDetailsCardContentKtTest {
    
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()
    
    lateinit var uiState: MutableStateFlow<Resource<Sport>>
    
    @Before
    fun setup() {
        uiState = MutableStateFlow(Resource.Loading)
        composeRule.setContent {
            val state by uiState.collectAsState()
            SportDetailsCardContent(state)
        }
    }
    
    @Test
    fun loadingState_showsLoadingIndicator() {
        uiState.update { Resource.Loading }
        
        composeRule.onNodeWithTextForStringId(R.string.loading)
            .assertExists()
    }
    
    @Test
    fun errorState_showsErrorText() {
        uiState.update { Resource.Error }
        
        composeRule.onNodeWithTextForStringId(R.string.error)
            .assertExists()
    }
    
    @Test
    fun readyState_showsSportDetails() {
        val sport = Sport("Test Sport", "Test Description")
        uiState.update { Resource.Ready(sport) }
        
        composeRule.onNodeWithText(sport.name)
            .assertExists()
        composeRule.onNodeWithText(sport.description)
            .assertExists()
    }
    
}
