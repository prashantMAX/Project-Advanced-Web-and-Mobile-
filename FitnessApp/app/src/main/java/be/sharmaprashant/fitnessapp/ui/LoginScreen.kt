package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.network.LoginRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import be.sharmaprashant.fitnessapp.viewModel.LoginViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = viewModel(), exerciseviewModel: ExerciseViewModel = viewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = dateFormat.format(Date())
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            coroutineScope.launch {
                try {
                    val response = RetrofitClient.apiService.login(LoginRequest(username, password))
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null && body.get("success").asBoolean) {
                            val token = body.get("token").asString
                            viewModel.fetchUserProfile(token)
                            exerciseviewModel.fetchExercises(token, date)
                            navController.navigate("home")
                        } else {
                            errorMessage = body?.get("message")?.asString ?: "Unknown error"
                        }
                    } else {
                        errorMessage = "Login failed"
                    }
                } catch (e: Exception) {
                    errorMessage = "Exception: ${e.message}"
                }
            }
        }) {
            Text("Login")
        }
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    LoginScreen(navController)
}
