package com.astrick.featuredsport.ui.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsTennis
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.astrick.featuredsport.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SportTopBar(
    onClickRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.SportsTennis,
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.top_bar_icon_size))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = stringResource(id = R.string.cd_refresh_sport),
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.top_bar_icon_size))
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onClickRefresh() }
            )
        },
        modifier = modifier
    )
}

// Preview
@Preview
@Composable
fun SportTopBarPreview() {
    SportTopBar(onClickRefresh = {})
}
