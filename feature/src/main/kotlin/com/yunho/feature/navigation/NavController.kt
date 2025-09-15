package com.yunho.feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.navOptions

fun NavController.navigateToHome(
    navOptions: NavOptions = navOptions {
        launchSingleTop = true
    }
) {
    navigate(
        route = Route.Home,
        navOptions = navOptions
    )
}
