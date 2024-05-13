package be.sharmaprashant.fitnessapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.ui.AccountInfoScreen
import be.sharmaprashant.fitnessapp.ui.LoginScreen

@Composable
fun FitnessApp(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("accountInfo") {
            AccountInfoScreen()
        }
    }
}