package be.sharmaprashant.fitnessapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.network.LoginRequest
import be.sharmaprashant.fitnessapp.network.LoginResponse
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    var username: MutableState<String> = mutableStateOf("")
        private set
    var password: MutableState<String> = mutableStateOf("")
    var loginResult by mutableStateOf<LoginResponse?>(null)
    fun login() {
        viewModelScope.launch {
            try {
                val request = LoginRequest(username.value, password.value)
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
    }

}