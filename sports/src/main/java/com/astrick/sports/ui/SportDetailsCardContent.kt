package com.astrick.sports.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.astrick.sports.R
import com.astrick.sports.data.Resource
import com.astrick.sports.data.Sport

/**
 * Composable function responsible for displaying the appropriate UI based
 * on the current state of the sports details, such as
 * error messages, loading indicators, or the actual content when ready.
 *
 * @param randomSport The current state of the sports details as a Resource object.
 * @param modifier The modifier to be applied to the content.
 */
@Composable
internal fun SportDetailsCardContent(
    randomSport: Resource<Sport>,
    modifier: Modifier = Modifier,
) {
    val springAnimation = spring<IntSize>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMediumLow
    )
    val padding = dimensionResource(id = R.dimen.padding_small)
    Card(
        modifier = modifier.animateContentSize(animationSpec = springAnimation),
    ) {
        when (randomSport) {
            Resource.Loading -> {
                LoadingState(modifier = Modifier.padding(padding))
            }
            
            is Resource.Ready -> {
                val content = randomSport.content
                ReadyState(
                    sport = content,
                    modifier = Modifier
                        .padding(padding)
                        .animateContentSize(animationSpec = springAnimation)
                )
            }
            
            Resource.Error -> {
                ErrorState(modifier = Modifier.padding(padding))
            }
        }
    }
}

@Composable
private fun ReadyState(
    sport: Sport,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = sport.name,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = sport.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_tiny)
            )
        )
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.loading),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

@Composable
private fun ErrorState(
    modifier: Modifier
) {
    Text(
        text = stringResource(id = R.string.error),
        style = MaterialTheme.typography.titleMedium,
        modifier = modifier
    )
}

// Previews
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ReadyPreview() {
    val sport = Sport("Awesome Sport", "This is an awesome sport")
    val state = Resource.Ready(sport)
    SportDetailsCardContent(
        randomSport = state,
        modifier = Modifier.padding(8.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun LoadingPreview() {
    val state = Resource.Loading
    SportDetailsCardContent(
        randomSport = state
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ErrorPreview() {
    val state = Resource.Error
    SportDetailsCardContent(
        randomSport = state
    )
}
