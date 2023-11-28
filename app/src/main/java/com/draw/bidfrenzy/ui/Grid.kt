package com.draw.bidfrenzy.ui

//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.Layout
//
//@Composable
//fun Grid(
//    modifier: Modifier = Modifier,
//    columns: Int = 1,
//    content: @Composable () -> Unit
//) {
//    Layout(
//        modifier = modifier,
//        content = content
//    ) {measurables, constraints ->
//
//        layout(constraints.maxWidth, constraints.maxHeight) {
//
//            val placeables = measurables.map { measurable ->
//                measurable.measure(constraints)
//            }
//
//            var index = 0
//
//            val width = constraints.maxWidth / columns
//
//            if(placeables.isNotEmpty()) {
//                for(i in 0 until  placeables.size / columns) {
//                    val rowPlaceables = placeables.slice((i * columns) until ((i * columns) + columns))
//                    for (j in 0 until  columns) {
//                        if(index >= placeables.size - 1) {
//                            break
//                        }
//
//                        println(measurables[index].maxIntrinsicHeight(width))
//
//                        placeables[index].placeRelative(
//                             j * width, i * width , 1f
//                        )
//                        index += 1
//                    }
//                }
//            }
//        }
//    }
//}