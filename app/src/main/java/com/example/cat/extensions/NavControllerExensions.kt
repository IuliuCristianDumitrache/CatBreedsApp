package com.example.cat.extensions

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator

fun NavController.destinationExists(destinationId: Int) = try {
    getBackStackEntry(destinationId)
    true
} catch (exc: Exception) {
    // no-op
    false
}

fun NavController.navigateSafely(actionId: Int, args: Bundle? = null, navOptions: NavOptions? = null) {
    currentDestination?.getAction(actionId)?.run {
        navigate(this.destinationId, args, navOptions)
    }
}

fun NavController.navigateSafely(navDirections: NavDirections, navOptions: NavOptions? = null) {
    currentDestination?.getAction(navDirections.actionId)?.run {
        navigate(navDirections, navOptions)
    }
}

fun NavController.navigateSafely(navDirections: NavDirections, navExtras: Navigator.Extras) {
    currentDestination?.getAction(navDirections.actionId)?.run {
        navigate(navDirections, navExtras)
    }
}