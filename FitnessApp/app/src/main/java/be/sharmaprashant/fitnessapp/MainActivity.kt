import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.LoginScreen

import be.sharmaprashant.fitnessapp.ui.theme.FitnessAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("accountInfo") {

                            AccountInfoScreen()
                        }
                    }
                }
            }
        }
    }
}