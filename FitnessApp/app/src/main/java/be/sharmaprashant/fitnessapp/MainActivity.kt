package be.sharmaprashant.fitnessapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.ui.AccountInfoScreen
import be.sharmaprashant.fitnessapp.ui.AddExerciseScreen
import be.sharmaprashant.fitnessapp.ui.ExerciseScreen
import be.sharmaprashant.fitnessapp.ui.HomePage
import be.sharmaprashant.fitnessapp.ui.LoginScreen
import be.sharmaprashant.fitnessapp.ui.theme.FitnessAppTheme
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import be.sharmaprashant.fitnessapp.viewModel.LoginViewModel

class MainActivity : ComponentActivity() {
    private val userProfileViewModel: LoginViewModel by viewModels()
    private val exerciseViewModel: ExerciseViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController: NavHostController = rememberNavController()
                    NavHost(navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController, userProfileViewModel,exerciseViewModel)
                        }
                        composable("accountInfo") {
                            val userProfile = userProfileViewModel.userProfile.value
                            userProfile?.let {
                                AccountInfoScreen(userProfile = it, navController)
                            }
                        }
                        composable("addExerciseScreen") {
                            AddExerciseScreen(navController = navController)
                        }
                        composable("exercise") {

                            val exercise = exerciseViewModel.exercises.value
                            val dummyData = listOf(
                                Exercises(1, 1, "Mock", 0.1),
                                Exercises(1, 1, "Mock", 0.50),
                                )

                            val exerciseList = if (exercise.isNullOrEmpty()) dummyData else exercise

                            ExerciseScreen(
                                exercise = exerciseList,
                                navController = navController
                            )
                        }
                        composable("home") {
                            val userProfile = userProfileViewModel.userProfile.value
                            userProfile?.let {
                                HomePage(navController = navController, userProfile = it)
                            }
                        }
                    }
                }
            }
        }
    }
}