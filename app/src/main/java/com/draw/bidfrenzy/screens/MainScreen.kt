package com.draw.bidfrenzy.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.draw.bidfrenzy.R
import com.draw.bidfrenzy.ui.BottomNavigationBar
import com.draw.bidfrenzy.ui.IconPlacement
import com.draw.bidfrenzy.ui.TopNavigationBar
import com.draw.bidfrenzy.viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    cartViewModel: CartViewModel = viewModel()
) {

    val navHostController = rememberNavController()

    var bottomNavigationBarVisible by remember {
        mutableStateOf(true)
    }

    val hideBottomBar: (Boolean) -> Unit = { hide ->
        bottomNavigationBarVisible = hide
    }

    Scaffold(
        topBar = {
            TopNavigationBar()
        },
        bottomBar = {
            Column {
                FloatingActionButton(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(20),
                    onClick = {
                        hideBottomBar(!bottomNavigationBarVisible)
                    }
                ) {
                    Crossfade(
                        targetState = bottomNavigationBarVisible)
                    { visible ->
                        Icon(
                            imageVector= when(visible) {
                                true -> Icons.Filled.KeyboardArrowDown
                                else -> Icons.Filled.KeyboardArrowUp
                            }
                            ,
                            contentDescription = when(visible) {
                                true -> "Hide Bottom Bar"
                                else -> "Show Bottom Bar"
                            }
                        )
                    }
                }
                AnimatedVisibility(
                    visible = bottomNavigationBarVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    BottomNavigationBar(
                        hideBottomBar = hideBottomBar,
                        navHostController = navHostController
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    UseNavigate.navigate(navHostController, NavRoutes.Spin)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_swipe_vertical_24),
                    contentDescription = "Spin and Win"
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            FrenzyNavHost(
                cartViewModel = cartViewModel,
                navHostController = navHostController
            )
        }
    }
}