package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = viewModel.username.value,
            onValueChange = {  viewModel.username.value = it},
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = { Text("Username") }
        )
        TextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.password.value = it},
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
        )
        Button(
            onClick = {viewModel.login()
                if (viewModel.loginResult?.success == true) {
                navController.navigate("accountInfo")
            }},
            modifier = Modifier
                .padding(vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Login")
        }
        viewModel.loginResult?.let {
            if (!it.success) {
                Text(it.message ?: "Login failed")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}

