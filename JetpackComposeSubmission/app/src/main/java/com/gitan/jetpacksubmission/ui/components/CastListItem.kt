package com.gitan.jetpacksubmission.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.gitan.jetpacksubmission.model.Cast
import com.gitan.jetpacksubmission.ui.theme.JetpackSubmissionTheme

@Composable
fun CastListItem(
    photoUrl: String,
    nameInSeries: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = nameInSeries,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp)
                    .padding(8.dp)
            )
            Column {
                Text(
                    text = nameInSeries,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Divider()
                Text(
                    text = description,
                    fontSize = 16.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CastListItemPreview() {
    JetpackSubmissionTheme() {
        CastListItem(
            "https://asianwiki.com/images/6/61/Keita_Machida-p01.jpg",
            "Keita Machida",
            "A Japanese actor. He is a member of the theater group Gekidan Exile. Machida is represented with LDH.",
        )
    }
}