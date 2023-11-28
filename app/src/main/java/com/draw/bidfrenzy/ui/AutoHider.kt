package com.draw.bidfrenzy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun AutoHider(
    modifier: Modifier = Modifier,
    floatingIconPlacement: IconPlacement = IconPlacement.Top,
    floatingIcon: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Layout(
        modifier = Modifier.then(modifier),
        content = {

            content()
            floatingIcon()
        }
    ) { measurables, constraints ->

        val placeables = measurables.map {  measurable ->
            measurable.measure(constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(0, 0)
            }
        }
    }
}

//val cords = when(floatingIconPlacement) {
//    IconPlacement.Start -> listOf(placeable.width , (contentH/2 - placeable.height/2))
//    IconPlacement.Top -> listOf(((contentW/2) - placeable.width/2), -(placeable.height))
//    IconPlacement.End -> listOf(contentW, (contentH/2 - placeable.height/2))
//    IconPlacement.Bottom -> listOf(((contentW/2) - placeable.width/2), (placeable.height))
//}

//                shape = when(floatingIconPlacement) {
//                    IconPlacement.Start -> RoundedCornerShape(topStartPercent = 50, bottomStartPercent = 50)
//                    IconPlacement.Top -> RoundedCornerShape(topStartPercent = 50, topEndPercent = 50)
//                    IconPlacement.End -> RoundedCornerShape(topEndPercent = 50, bottomEndPercent = 50)
//                    IconPlacement.Bottom -> RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50)
//                }