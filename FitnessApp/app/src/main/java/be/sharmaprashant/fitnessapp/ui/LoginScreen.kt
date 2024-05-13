package be.sharmaprashant.fitnessapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.network.LoginRequest
import be.sharmaprashant.fitnessapp.network.LoginResponse
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loginResult by remember { mutableStateOf<LoginResponse?>(null) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = { Text("Username") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
        )
        Button(
            onClick = {
                scope.launch {
                    try {
                        val request = LoginRequest(username, password)
                        val response: Response<JsonObject> = RetrofitClient.apiService.login(request)
                        loginResult = if (response.isSuccessful) {
                            val jsonResponse = response.body()
                            val success = jsonResponse?.get("success")?.asBoolean ?: false
                            val message = if (success) "Login successful" else "Login failed"
                            val token = jsonResponse?.get("token")?.asString
                            LoginResponse(success, message, token)
                        } else {
                            LoginResponse(false, "Error: ${response.code()}", null)
                        }
                        // If login is successful, navigate to "accountInfo" and pop back stack
                        if (loginResult?.success == true) {
                            navController.navigate("accountInfo") {
                                // Pop the back stack up to the login destination
                                popUpTo("login") { inclusive = true }
                                // Navigate to the account info holder destination
                                launchSingleTop = true
                            }

                        }
                    } catch (e: Exception) {
                        loginResult = LoginResponse(false, "Error: ${e.message}", null)
                    }
                }
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Login")
        }
        loginResult?.let {
            if (!it.success) {
                Text(it.message ?: "Login failed")
            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController();
    LoginScreen(navController)

}
