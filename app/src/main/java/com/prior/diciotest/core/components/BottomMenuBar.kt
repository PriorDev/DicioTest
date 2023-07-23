package com.prior.diciotest.core.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.prior.diciotest.R
import com.prior.diciotest.core.NavItem
import com.prior.diciotest.core.RoutesMenu

@Composable
fun BottomMenuBar(
    navController: NavHostController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()

    val menuItems = listOf(
        NavItem(
            name = stringResource(id = R.string.registration),
            route = RoutesMenu.RegisterView.route,
            icon = Icons.Rounded.AddCircle,
        ),
        NavItem(
            name = stringResource(id = R.string.list_of_users),
            route = RoutesMenu.ListUserView.route,
            icon = Icons.Rounded.Person,
        ),
    )

    NavigationBar(

    ) {
        menuItems.forEachIndexed { index, navItem ->
            val selected = navItem.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { navController.navigate(navItem.route) },
                label = {
                    Text(
                        text = navItem.name,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.name,
                    )
                }
            )
        }

    }
}