package com.bitinovus.naranja

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bitinovus.naranja.presentation.ui.components.stack.StackContainer
import com.bitinovus.naranja.presentation.ui.components.stack.StackImage
import com.bitinovus.naranja.presentation.viewmodels.profileviewmodel.ProfileViewModel
import com.bitinovus.naranja.ui.theme.NaranjaTheme
import kotlinx.coroutines.launch
import kotlin.math.abs

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

                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth()
                            .fillMaxSize()
                            .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
                    ) {
                        var offset = 0.dp

                        for (profile in profileState.data) {

                            val screenHeight = LocalConfiguration.current.screenHeightDp.toFloat()
                            val scope = rememberCoroutineScope()
                            val translationY = remember { Animatable(0f) }
                            translationY.updateBounds(-screenHeight, screenHeight)

                            val draggableState = rememberDraggableState { delta ->
                                scope.launch {
                                    translationY.snapTo(translationY.value + delta)
                                }
                            }

                            StackContainer(
                                modifier = Modifier
                                    .offset(y = offset) // image offset
                                    .offset { // animation offset
                                        IntOffset(x = 0, y = translationY.value.toInt())
                                    }
                                    .draggable(
                                        state = draggableState,
                                        orientation = Orientation.Vertical,
                                        onDragStopped = { velocity ->
                                            val follow =
                                                translationY.value < -screenHeight / 3f
                                            val remove = translationY.value > screenHeight / 3f

                                            scope.launch {
                                                when {
                                                    follow -> {
                                                        translationY.animateTo(
                                                            -screenHeight,
                                                            initialVelocity = velocity,
                                                        )
                                                        Log.d(
                                                            "ACTION",
                                                            "onCreate: ADD"
                                                        )
                                                        // TEST
                                                        profileViewModel.delete(profile)
                                                    }

                                                    remove -> {
                                                        translationY.animateTo(
                                                            screenHeight,
                                                            initialVelocity = velocity,
                                                        )
                                                        Log.d(
                                                            "ACTION",
                                                            "onCreate: REMOVE"
                                                        )
                                                        // TEST
                                                        profileViewModel.delete(profile)
                                                    }

                                                    else -> {
                                                        translationY.animateTo(0f)
                                                    }
                                                }
                                            }
                                        }
                                    )
                                    .graphicsLayer {
                                        alpha = 1f - (abs(translationY.value) / screenHeight)
                                    }
                            ) {
                                StackImage(profile = profile)
                            }
                            offset += 25.dp / 2
                        }
                    }
                }
            }
        }
    }
}
