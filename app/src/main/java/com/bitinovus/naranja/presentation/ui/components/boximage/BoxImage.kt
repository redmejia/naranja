package com.bitinovus.naranja.presentation.ui.components.boximage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

// Use for VerticalPager
@Composable
fun BoxImage(
    name: String = "",
    image: String = "",
    hobbies: List<String> = emptyList(),
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = image,
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
                    text = name,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(items = hobbies) {
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


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BoxImagePreview() {
    BoxImage(
        image = "https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/001.png",
        name = "Sofia Doe",
        hobbies = listOf("Swimming", "Travelling", "Reading")
    )
}