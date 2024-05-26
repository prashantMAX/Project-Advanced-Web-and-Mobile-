package be.sharmaprashant.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.ui.AccountInfoScreen
import be.sharmaprashant.fitnessapp.ui.LoginScreen
import be.sharmaprashant.fitnessapp.ui.theme.FitnessAppTheme
import be.sharmaprashant.fitnessapp.viewmodel.UserProfileViewModel

class MainActivity : ComponentActivity() {
    private val userProfileViewModel: UserProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController: NavHostController = rememberNavController()
                    NavHost(navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController, userProfileViewModel)
                        }
                        composable("accountInfo") {
                            val userProfile = userProfileViewModel.userProfile.value
                            userProfile?.let {
                                AccountInfoScreen(userProfile = it)
                            }
                        }
                    }
                }
            }
        }
    }
}
