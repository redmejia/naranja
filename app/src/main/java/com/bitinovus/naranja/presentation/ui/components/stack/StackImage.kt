package com.bitinovus.naranja.presentation.ui.components.stack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bitinovus.naranja.data.remote.model.Profile

@Composable
fun StackImage(
    modifier: Modifier = Modifier,
    profile: Profile) {
    Box {
        AsyncImage(
            modifier = modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = profile.image,
            contentDescription = null
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(bottom = 20.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
            ) {
                Text(
                    text = profile.name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = profile.hobbies) {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Normal,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}