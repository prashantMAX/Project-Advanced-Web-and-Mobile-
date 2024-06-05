package be.sharmaprashant.fitnessapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.ui.AccountInfoScreen
import be.sharmaprashant.fitnessapp.ui.AddExerciseScreen
import be.sharmaprashant.fitnessapp.ui.AddFoodScreen
import be.sharmaprashant.fitnessapp.ui.ExerciseScreen
import be.sharmaprashant.fitnessapp.ui.HomePage
import be.sharmaprashant.fitnessapp.ui.LoginScreen
import be.sharmaprashant.fitnessapp.ui.OverviewScreen
import be.sharmaprashant.fitnessapp.ui.theme.FitnessAppTheme
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import be.sharmaprashant.fitnessapp.viewModel.FoodViewModel
import be.sharmaprashant.fitnessapp.viewModel.LoginViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val userProfileViewModel: LoginViewModel by viewModels()
    private val exerciseViewModel: ExerciseViewModel by viewModels()
    private val foodViewModel: FoodViewModel by viewModels()
    
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController: NavHostController = rememberNavController()
                    NavHost(navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController, userProfileViewModel, exerciseViewModel, foodViewModel)
                        }
                        composable("accountInfo") {
                            val userProfile = userProfileViewModel.userProfile.value
                            userProfile?.let {
                                AccountInfoScreen(userProfile = it, navController)
                            }
                        }
                        composable("addExerciseScreen/{date}") { backStackEntry ->
                            val date = backStackEntry.arguments?.getString("date") ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            AddExerciseScreen(
                                navController = navController,
                                viewModel = exerciseViewModel,
                                initialDate = date,
                                onExerciseAdded = {
                                }
                            )
                        }

                        composable("addFoodScreen/{date}") { backStackEntry ->
                            val date = backStackEntry.arguments?.getString("date") ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            AddFoodScreen(
                                navController = navController,
                                viewModel = foodViewModel,
                                initialDate = date,
                                onFoodAdded = {
                                }
                            )
                        }
                        composable("exercise") {
                            val exercise = exerciseViewModel.exercises.observeAsState().value
                            val food = foodViewModel.food.observeAsState().value

                            if (exercise != null && food != null) {
                                ExerciseScreen(

                                    navController = navController,

                                    exerciseViewModel = exerciseViewModel,
                                    foodViewModel = foodViewModel
                                )
                            } else {
                                Text("Error: Exercise or food data is missing.")
                            }
                        }
                        composable("overview") {
                            val exercise = exerciseViewModel.exercises.observeAsState().value
                            if (exercise != null ) {
                                OverviewScreen(
                                    exercise = exercise,
                                    navController = navController,
                                    exerciseviewModel = exerciseViewModel
                                )
                            } else {
                                Text("Error: Exercise or food data is missing.")
                            }
                        }
                        composable("home") {
                            val userProfile = userProfileViewModel.userProfile.observeAsState().value
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
