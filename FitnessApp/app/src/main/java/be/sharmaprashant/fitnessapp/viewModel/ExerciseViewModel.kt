package be.sharmaprashant.fitnessapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.network.AddExerciseRequest
import be.sharmaprashant.fitnessapp.network.ExercisesAndFoodRequest
import be.sharmaprashant.fitnessapp.network.ExercisesPerDateRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ExerciseViewModel : ViewModel() {
    private val _exercises = MutableLiveData<List<Exercises>>()
    val exercises: LiveData<List<Exercises>> get() = _exercises


    var token: String? = null

    fun fetchExercises(token: String, date: String) {
        this.token = token

        viewModelScope.launch {
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
                    }
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }

   fun addExercises(exerciseName: String, caloriesPerRep: Double, date: String) {
        val token = this.token
        if (token == null) {
            return
        }
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.addExercise(
                    AddExerciseRequest(
                        token,
                        exerciseName,
                        caloriesPerRep,
                        date
                    )
                )
                if (response.isSuccessful) {
                    fetchExercises(token, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }
}

