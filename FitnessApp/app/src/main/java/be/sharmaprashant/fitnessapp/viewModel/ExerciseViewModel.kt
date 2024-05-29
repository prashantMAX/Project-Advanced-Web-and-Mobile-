package be.sharmaprashant.fitnessapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.network.AddExerciseRequest
import be.sharmaprashant.fitnessapp.network.ExercisesAndFoodRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import be.sharmaprashant.fitnessapp.network.TokenRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseViewModel : ViewModel() {
    private val TAG = "ExerciseViewModel"

    private val _exercises = MutableLiveData<List<Exercises>>()
    val exercises: LiveData<List<Exercises>> get() = _exercises

    var token: String? = null

    fun fetchExercises(token: String, date: String) {
        this.token = token

        viewModelScope.launch {
            Log.d(TAG, "Fetching exercises with token: $token")
            try {
                val response: Response<JsonObject> = RetrofitClient.apiService.getExercise(
                    ExercisesAndFoodRequest(token, date)
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.get("success").asBoolean) {
                        val exercisesJson = body.getAsJsonArray("exercises")
                        val exercisesList = mutableListOf<Exercises>()
                        exercisesJson.forEach { exerciseElement ->
                            val exerciseObject = exerciseElement.asJsonObject
                            val exercise = Exercises(
                                userId = exerciseObject.get("userId").asInt,
                                exercise_id = exerciseObject.get("exercise_id").asInt,
                                exercise_name = exerciseObject.get("exercise_name").asString,
                                calories_per_rep = exerciseObject.get("calories_per_rep").asDouble,
                                date = exerciseObject.get("date_id").asInt
                            )
                            exercisesList.add(exercise)
                        }
                        _exercises.value = exercisesList
                    } else {
                        Log.e(TAG, "API success flag is false")
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}", e)
            }
        }
    }

    fun addExercises(exerciseName: String, caloriesPerRep: Double, date: String) {
        val token = this.token
        if (token == null) {
            Log.e(TAG, "Token is null, cannot add exercise")
            return
        }
        viewModelScope.launch {
            try {
                Log.d(TAG, "Adding exercise with token: $token")
                Log.d(TAG, "Adding exercise with token: $date")

                val response = RetrofitClient.apiService.addExercise(
                    AddExerciseRequest(
                        token,
                        exerciseName,
                        caloriesPerRep,
                        date
                    )
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Exercise added successfully")
                    // Fetch exercises after adding a new one
                    fetchExercises(token, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
                } else {
                    Log.e(TAG, "Failed to add exercise: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}", e)
            }
        }
    }
}

