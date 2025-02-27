package com.bitinovus.naranja

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.bitinovus.naranja.data.remote.api.OrangeAPI
import com.bitinovus.naranja.data.remote.config.APIConfig
import com.bitinovus.naranja.ui.theme.NaranjaTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val apiConfig = APIConfig.createService(OrangeAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            try {
                val response = apiConfig.getProfiles()
                if (response.isSuccessful) {
                    Log.d("API_RESPONSE", "Data: ${response.body()}")
                } else {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()} ")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Exception: ${e.message}")
            }
        }
        setContent {
            NaranjaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
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