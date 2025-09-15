package com.yunho.feature

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yunho.feature.home.view.Home
import com.yunho.feature.navigation.LocalNavController
import com.yunho.feature.navigation.Route

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            modifier = modifier,
            startDestination = Route.Home
        ) {
            composable<Route.Home> {
                Home(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
