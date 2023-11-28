package com.draw.bidfrenzy.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.draw.bidfrenzy.screens.NavRoutes
import com.draw.bidfrenzy.screens.UseNavigate

@Composable
fun BottomNavigationBar(
    hideBottomBar: (Boolean) -> Unit,
    navHostController: NavHostController
) {

    NavigationBar(
        modifier = Modifier
    ) {

        val backStackEntry by navHostController.currentBackStackEntryAsState()

        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.NavItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route.route,
                onClick = {
                    UseNavigate.navigate(
                        navHostController = navHostController,
                        navItem.route
                    )
                    hideBottomBar(false)
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(
                        text = navItem.title
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar() {

    var search by remember {
        mutableStateOf("")
    }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 0.dp, color = Color.Transparent),
        value = search,
        placeholder = {
                      Text("Search")
        },
        onValueChange = {
            search = it
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search"
            )
        },
        singleLine = true
    )

    // shape = RoundedCornerShape(50)
}

object NavBarItems {
    val NavItems = listOf(
        NavItem(
            title = "Home",
            icon = Icons.Filled.Home,
            route = NavRoutes.Home
        ),
        NavItem(
            title = "Auctions",
            icon = Icons.Filled.Star,
            route = NavRoutes.Auctions
        ),
        NavItem(
            title = "Cart",
            icon = Icons.Filled.ShoppingCart,
            route = NavRoutes.Cart
        ),
        NavItem(
            title = "Account",
            icon = Icons.Filled.AccountCircle,
            route = NavRoutes.Account
        )
    )
}

data class NavItem(
    val title: String,
    val route: NavRoutes,
    val icon: ImageVector
)

@Preview(showBackground = true)
@Composable
fun BarPreview() {
    TopNavigationBar()
}