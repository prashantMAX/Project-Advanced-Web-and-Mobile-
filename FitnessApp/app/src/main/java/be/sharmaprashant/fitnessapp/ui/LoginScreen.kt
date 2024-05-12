package be.sharmaprashant.fitnessapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import be.sharmaprashant.fitnessapp.network.ApiService
import be.sharmaprashant.fitnessapp.network.LoginRequest
import be.sharmaprashant.fitnessapp.network.LoginResponse
import be.sharmaprashant.fitnessapp.network.RetrofitClient
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
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                scope.launch {
                    try {
                        val request = LoginRequest(username, password)
                        val response: Response<LoginResponse> = RetrofitClient.apiService.login(request)
                        if (response.isSuccessful) {
                            loginResult = response.body()
                            if (loginResult?.success == true) {
                                navController.navigate("accountInfo")
                            }
                        } else {
                            // Handle unsuccessful login
                        }
                    } catch (e: Exception) {
                        // Handle exceptions
                    }
                }
            },
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Login")
        }
        loginResult?.let { result ->
            if (result.success) {
                Text("Login successful")
            } else {
                Text("Login failed: ${result.message ?: "Unknown error"}")
            }
        }
    }
}
