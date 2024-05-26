package be.sharmaprashant.fitnessapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.ui.AccountInfoScreen
import be.sharmaprashant.fitnessapp.ui.AddExerciseScreen
import be.sharmaprashant.fitnessapp.ui.ExerciseScreen
import be.sharmaprashant.fitnessapp.ui.HomePage
import be.sharmaprashant.fitnessapp.ui.LoginScreen
import be.sharmaprashant.fitnessapp.viewModel.LoginViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FitnessApp(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("home") {
            HomePage(navController = navController)
        }
        composable("accountInfo") {
            AccountInfoScreen(navController = navController, viewModel = viewModel())
        }
        composable("addExerciseScreen") {
            AddExerciseScreen(navController = navController)
        }
        composable("exercise") {
            ExerciseScreen(navController = navController)
        }
    }
}

