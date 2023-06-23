package com.gitan.jetpacksubmission.ui.screen.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gitan.jetpacksubmission.R
import com.gitan.jetpacksubmission.ui.theme.JetpackSubmissionTheme

@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    ProfileContent(
        image = R.drawable.foto_sq,
        name = stringResource(id = R.string.name),
        email = stringResource(id = R.string.email),
        university = stringResource(id = R.string.university),
        major = stringResource(id = R.string.major),
        birthDate = stringResource(id = R.string.birth_date),
        onBackClick = navigateBack)
}

@Composable
fun ProfileContent(
    @DrawableRes image: Int,
    name: String,
    email: String,
    university: String,
    major: String,
    birthDate: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
            Image(
                painter = painterResource(id = image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .size(200.dp)
                    .clip(RoundedCornerShape(100.dp))
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = email,
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
                text = "$major, $university",
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    JetpackSubmissionTheme() {
        ProfileContent(
            image = R.drawable.foto_sq,
            name = stringResource(id = R.string.name),
            email = stringResource(id = R.string.email),
            university = stringResource(id = R.string.university),
            major = stringResource(id = R.string.major),
            birthDate = stringResource(id = R.string.birth_date),
            onBackClick = {  }
        )
    }
}