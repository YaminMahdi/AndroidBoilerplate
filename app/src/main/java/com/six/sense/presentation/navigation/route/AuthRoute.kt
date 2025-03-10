package com.six.sense.presentation.navigation.route

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.six.sense.presentation.navigation.Screens
import com.six.sense.presentation.screen.auth.LoginScreen
import com.six.sense.presentation.screen.auth.LoginViewModel
import com.six.sense.presentation.screen.onboarding.OnboardingScreen
import com.six.sense.presentation.screen.onboarding.OnboardingViewModel
import com.six.sense.utils.composableWithVM

/**
 * Defines the navigation route for the authentication flow.
 *
 * @param modifier Modifier for the layout.
 */
fun NavGraphBuilder.authRoute(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    composableWithVM<Screens.Onboarding, OnboardingViewModel>(navController = navController) {

        OnboardingScreen(onBoardingCompleted = {
            viewModel.saveOnboarding(false)
            navController.navigate(Screens.Login) {
                popUpTo(Screens.Onboarding) {
                    inclusive = true
                }
            }
        })
    }
    composableWithVM<Screens.Login,
            LoginViewModel>(navController = navController) {
        val activity = LocalActivity.current as Activity
        val onSuccess = {
            navController.navigate(Screens.Home) {
                popUpTo(Screens.Login) {
                    inclusive = true
                }
            }
        }
        LoginScreen(
            onClickFacebookLogin = {
                viewModel.facebookSignIn(activity = activity, onSuccess = onSuccess)
            },
            onClickGoogleLogin = {
                viewModel.googleSignIn(activity = activity, onSuccess = onSuccess)
            },
            modifier = modifier
        )
    }
}