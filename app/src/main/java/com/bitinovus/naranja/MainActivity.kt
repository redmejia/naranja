package com.bitinovus.naranja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bitinovus.naranja.presentation.viewmodels.profileviewmodel.ProfileViewModel
import com.bitinovus.naranja.ui.theme.NaranjaTheme

class MainActivity : ComponentActivity() {
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NaranjaTheme {
                profileViewModel = viewModel<ProfileViewModel>()

                val profileState by profileViewModel.profileState.collectAsState()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn (
                        modifier = Modifier.padding(innerPadding),
                    ){
                        items(items = profileState.data) { profile ->
                            AsyncImage(
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                model = profile.image,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NaranjaTheme {
        Greeting("Android")
    }
}