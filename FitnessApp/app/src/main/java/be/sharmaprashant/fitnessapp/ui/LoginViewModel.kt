package be.sharmaprashant.fitnessapp.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.data.LoginUIState
import be.sharmaprashant.fitnessapp.network.LoginRequest
import be.sharmaprashant.fitnessapp.network.LoginResponse
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _loginUIState = MutableStateFlow(LoginUIState())
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()
    fun setUsername(username: String) {
        val currentState = _loginUIState.value
        _loginUIState.value = currentState.copy(username = username)
    }

    fun setPassword(password: String) {
        val currentState = _loginUIState.value
        _loginUIState.value = currentState.copy(password = password)
    }
    fun login() {
        viewModelScope.launch {
            try {
                val username = _loginUIState.value.username
                val password = _loginUIState.value.password
                val request = LoginRequest(username, password)
                val response: Response<JsonObject> = RetrofitClient.apiService.login(request)
                val loginResult = if (response.isSuccessful) {
                    val jsonResponse = response.body()
                    val success = jsonResponse?.get("success")?.asBoolean ?: false
                    val message = if (success) "Login successful" else "Login failed"
                    val token = jsonResponse?.get("token")?.asString
                    LoginResponse(success, message, token)
                } else {
                    LoginResponse(false, "Error: ${response.code()}", null)
                }
                _loginUIState.value = _loginUIState.value.copy(loginResult = loginResult)
            } catch (e: Exception) {
                _loginUIState.value = _loginUIState.value.copy(
                    loginResult = LoginResponse(false, "Error: ${e.message}", null)
                )
            }
        }
    }

}