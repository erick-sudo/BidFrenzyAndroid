package com.draw.bidfrenzy.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Table(
    rowSeparator: @Composable () -> Unit = {},
    tableData: Map<String, String>
) {
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        for ((k, v) in tableData) {
            rowSeparator()
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = k.split(" ").joinToString { word -> word.replaceFirstChar { ch -> ch.uppercase() } },
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = v,
                    modifier = Modifier
                        .weight(2f)
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}