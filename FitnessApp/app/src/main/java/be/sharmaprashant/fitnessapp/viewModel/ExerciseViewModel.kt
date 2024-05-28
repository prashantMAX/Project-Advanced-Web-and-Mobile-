package be.sharmaprashant.fitnessapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.network.AddExerciseRequest
import be.sharmaprashant.fitnessapp.network.ExercisesRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import be.sharmaprashant.fitnessapp.network.TokenRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.Date

class ExerciseViewModel : ViewModel() {
    private val TAG = "ExerciseViewModel"

    private val _exercises = MutableLiveData<List<Exercises>>()
    val exercises: MutableLiveData<List<Exercises>> get() = _exercises
    private var _x = "";
    val tokens = _x;


    fun fetchExercises(token: String, date: String) {

        _x = token;
        viewModelScope.launch {
            try {
                Log.d(TAG, "Fetching exercises")
                val response: Response<JsonObject> = RetrofitClient.apiService.getExercise(
                    ExercisesRequest(token, date)
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Response successful: $response")
                    val body = response.body()
                    if (body != null) {
                        if (body.get("success").asBoolean) {
                            Log.d(TAG, "Parsing exercises")
                            val exercisesJson = body.getAsJsonArray("exercises")
                            val exercisesList = mutableListOf<Exercises>()
                            exercisesJson.forEach { exerciseElement ->
                                val exerciseObject = exerciseElement.asJsonObject
                                val exercises = Exercises(
                                    userId = exerciseObject.get("userId").asInt,
                                    exercise_id = exerciseObject.get("exercise_id").asInt,
                                    exercise_name = exerciseObject.get("exercise_name").asString,
                                    calories_per_rep = exerciseObject.get("calories_per_rep").asDouble,
                                    date = exerciseObject.get("date_id").asInt
                                )
                                exercisesList.add(exercises)
                            }
                            _exercises.value = exercisesList
                            Log.d(TAG, "Exercises parsed: $exercisesList")
                            printFetchedData(exercisesList)
                        } else {
                            Log.e(TAG, "API success flag is false")
                        }
                    } else {
                        Log.e(TAG, "Response body is null")
                    }
                } else {
                    Log.e(TAG, "Unsuccessful response: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception: ${e.message}", e)
                // Handle the exception
            }
        }
    }

    fun addExercises(exerciseName: String, caloriesPerRep: Double) {
        Log.d(TAG, "Adding exercise $tokens")
        Log.d(TAG, "Adding exercise $exerciseName")
        Log.d(TAG, "Adding exercise $caloriesPerRep")

        viewModelScope.launch {
            RetrofitClient.apiService.AddExercise(
                AddExerciseRequest(
                    tokens,
                    exerciseName,
                    caloriesPerRep
                )
            )
        }
    }

    private fun printFetchedData(exercisesList: List<Exercises>) {
        Log.d(TAG, "Fetched exercises:")
        exercisesList.forEachIndexed { index, exercises ->
            Log.d(TAG, "Exercise ${index + 1}: $exercises")
        }
    }
}