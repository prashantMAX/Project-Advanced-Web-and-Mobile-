package be.gauravghimire.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.gauravghimire.test.ui.theme.TestTheme
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

object RetrofitClient {
    private const val BASE_URL = "https://gauravghimire.be/API_Fitness/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val token: String?
)

data class LoginRequest(
    val username: String,
    val password: String
)

interface ApiService {
    @POST("Login.php")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): Response<JsonObject>
}

@Composable
fun LoginScreen() {
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
            if (it.success) {
                Text(it.message ?: "Login successful")
            } else {
                Text(it.message ?: "Login failed")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestTheme {
        LoginScreen()
    }
}