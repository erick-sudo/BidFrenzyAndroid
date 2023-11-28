package com.draw.bidfrenzy.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.draw.bidfrenzy.R

@Composable
fun ImageViewDialog(
    imageUrl: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            shape = RoundedCornerShape(10.dp),
            tonalElevation = 80.dp
        ) {
            Column(
                modifier = Modifier
            ) {
                Box(
                    modifier = Modifier
                        .padding(0.dp)
                        .height(350.dp)
                ) {
                    Zoom() {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Laptop",
                            modifier = Modifier
                                .padding(15.dp),
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    FloatingActionButton(
                        modifier = Modifier
                            .weight(1f),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {}
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.baseline_share_24),
                            contentDescription = "Share"
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .weight(1f),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {}
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.baseline_info_24),
                            contentDescription = "Info"
                        )
                    }
                    FloatingActionButton(
                        modifier = Modifier
                            .weight(1f),
                        shape = RoundedCornerShape(0.dp),
                        onClick = {}
                    ) {
                        Icon(
                            ImageVector.vectorResource(id = R.drawable.baseline_save_alt_24),
                            contentDescription = "Info"
                        )
                    }
                }
            }
        }
    }
}