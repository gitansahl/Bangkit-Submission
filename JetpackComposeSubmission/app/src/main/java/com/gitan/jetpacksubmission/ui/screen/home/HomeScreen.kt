package com.gitan.jetpacksubmission.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gitan.jetpacksubmission.di.Injection
import com.gitan.jetpacksubmission.model.Cast
import com.gitan.jetpacksubmission.ui.ViewModelFactory
import com.gitan.jetpacksubmission.ui.common.UiState
import com.gitan.jetpacksubmission.ui.components.CastListItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllCasts()
            }
            is UiState.Success -> {
                HomeContent(
                    modifier = modifier,
                    casts = uiState.data,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun HomeContent(
    casts: List<Cast>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(casts, key = { it.id }) {cast ->
            CastListItem(
                photoUrl = cast.photoUrl,
                nameInSeries = cast.nameInSeries,
                description = cast.description,
                modifier = Modifier.clickable { navigateToDetail(cast.id) },
            )
        }
    }
}