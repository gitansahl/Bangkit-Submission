package com.gitan.jetpacksubmission.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.gitan.jetpacksubmission.R
import com.gitan.jetpacksubmission.di.Injection
import com.gitan.jetpacksubmission.ui.ViewModelFactory
import com.gitan.jetpacksubmission.ui.common.UiState
import com.gitan.jetpacksubmission.ui.theme.JetpackSubmissionTheme

@Composable
fun DetailScreen(
    castId: Long,
    viewModel: DetailCastViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getCastById(castId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    nameInRealLife = data.nameInRealLife,
                    nameInSeries = data.nameInSeries,
                    birthDate = data.birthDate,
                    description = data.description,
                    photoUrl = data.photoUrl,
                    onBackClick = navigateBack
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun DetailContent(
    nameInRealLife: String,
    nameInSeries: String,
    birthDate: String,
    description: String,
    photoUrl: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.Start)
                .clickable { onBackClick() }
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = nameInRealLife,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(200.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = nameInRealLife,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.played_as, nameInSeries),
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = birthDate,
                fontWeight = FontWeight.Normal,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = description,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    JetpackSubmissionTheme() {
        DetailContent(
            "Keita Machida",
            "Daikichi Karube",
            "4 Juli 1990",
            "A Japanese actor. He is a member of the theater group Gekidan Exile. Machida is represented with LDH.",
            "https://asianwiki.com/images/6/61/Keita_Machida-p01.jpg",
            onBackClick = {}
        )
    }
}