package be.sharmaprashant.fitnessapp.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.data.UserProfile
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import be.sharmaprashant.fitnessapp.network.TokenRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class ExerciseViewModel : ViewModel() {
    private val TAG = "ExerciseViewModel"

    private val _exercises = MutableLiveData<Exercises>()
    val exercises: LiveData<Exercises> get() = _exercises

    fun fetchExercises(token: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Fetching exercises")
                val response: Response<JsonObject> = RetrofitClient.apiService.getExercise(
                    TokenRequest(token)
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Response successful")
                    val body = response.body()
                    body?.let {
                        if (it.get("success").asBoolean) {
                            Log.d(TAG, "Parsing exercises")
                            val exercisesJson = it.getAsJsonObject("user_profile")
                            val exercises = Exercises(
                                userId = exercisesJson.get("UserID").asInt,
                                exercise_id = exercisesJson.get("excercise_id").asInt,
                                exercise_name = exercisesJson.get("exercise_name").asString,
                                calories_per_rep = exercisesJson.get("calories_per_rep").asDouble,
                            )
                            _exercises.value = exercises
                            Log.d(TAG, "Exercises parsed: $exercises")
                        }
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}")
                // Handle the exception
            }
        }
    }
}

