package com.astrick.featuredsport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.astrick.featuredsport.ui.theme.FeaturedSportTheme
import com.astrick.featuredsport.ui.toolbar.SportTopBar
import com.astrick.sports.ui.SportDetailsCardContainer
import com.astrick.sports.ui.controllers.SportStateController

class MainActivity : ComponentActivity() {
    
    private val sportStateController = SportStateController("1", this)
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            FeaturedSportTheme {
                Scaffold(
                    topBar = {
                        SportTopBar(
                            onClickRefresh = {
                                sportStateController.randomize()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    SportDetailsCardContainer(
                        sportStateController = sportStateController,
                        modifier = Modifier.padding(
                            top = padding.calculateTopPadding(),
                            start = dimensionResource(id = R.dimen.padding_small),
                            end = dimensionResource(id = R.dimen.padding_small)
                        ).fillMaxWidth()
                    )
                }
            }
        }
    }
    
}
