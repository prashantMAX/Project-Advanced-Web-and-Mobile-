package be.sharmaprashant.fitnessapp.data

import be.sharmaprashant.fitnessapp.network.LoginResponse

data class LoginUIState(val username: String = "", val password: String = "", val loginResult: LoginResponse? = null)
