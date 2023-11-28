package com.draw.bidfrenzy.screens

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.extensions.toRadians
import com.draw.bidfrenzy.viewmodels.SpinViewModel
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Spin(
    spinViewModel: SpinViewModel = viewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    val imageBitmaps = listOf(
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo,
        R.drawable.logo
    ) .map { it -> ImageBitmap.imageResource(it) }

    val primaryColor = MaterialTheme.colorScheme.primary

    val isSpinning by spinViewModel.isSpinning.collectAsState()

    val offsetAngle by spinViewModel.angleFlow.collectAsState()

    val animatedAngle by animateFloatAsState(targetValue = offsetAngle)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(350.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                drawCircle(
                    color = primaryColor
                )

                val interval = 360 / imageBitmaps.size

                imageBitmaps.forEachIndexed { index, imageBitmap ->
                    val parentWidth = 350.dp.toPx()

                    drawImage(
                        image = imageBitmap,
                        topLeft = computeXY(
                            center = Offset(parentWidth/2, parentWidth/2),
                            radius = parentWidth/3f,
                            angle = (animatedAngle + (interval * index)).toRadians()
                        ) - Offset(60f, 60f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Crossfade(
            targetState = isSpinning
        ) { spinning ->
            if(spinning) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            spinViewModel.stop()
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_stop_24),
                        contentDescription = "Stop",
                        tint = Color.Red
                    )
                }
            } else {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            spinViewModel.start()
                        }
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_play_arrow_24),
                        contentDescription = "Play"
                    )
                }
            }
        }

    }
}


fun computeXY(center: Offset, radius: Float, angle: Float): Offset {
    return Offset(
        x = center.x + (radius * cos(angle)),
        y = center.y + (radius * sin(angle))
    )
}

@Preview(showSystemUi = true)
@Composable
fun SpinPreview() {
    Spin()
}