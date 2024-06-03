package be.sharmaprashant.fitnessapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.model.UserProfile
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import be.sharmaprashant.fitnessapp.model.TokenRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class UserProfileViewModel : ViewModel() {

    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile> get() = _userProfile

    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            try {
                val response: Response<JsonObject> = RetrofitClient.apiService.getUserData(TokenRequest(token))
                if (response.isSuccessful) {
                    val body = response.body()

                    body?.let {
                        if (it.get("success").asBoolean) {
                            val userProfileJson = it.getAsJsonObject("user_profile")
                            val userProfile = UserProfile(
                                profileID = userProfileJson.get("ProfileID").asInt,
                                userID = userProfileJson.get("UserID").asInt,
                                name = userProfileJson.get("Name").asString,
                                age = userProfileJson.get("Age").asInt,
                                weight = userProfileJson.get("Weight").asFloat,
                                height = userProfileJson.get("Height").asFloat,
                                gender = userProfileJson.get("Gender").asString,
                                activityLevel = userProfileJson.get("ActivityLevel").asString
                            )
                            _userProfile.value = userProfile
                        }
                    }
                }
            } catch (e: Exception) {
                // Handle the exception
            }
        }
    }
}
