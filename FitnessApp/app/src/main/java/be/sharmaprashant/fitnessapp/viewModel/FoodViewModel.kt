package be.sharmaprashant.fitnessapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.data.Food
import be.sharmaprashant.fitnessapp.data.UserProfile
import be.sharmaprashant.fitnessapp.network.ExercisesRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import be.sharmaprashant.fitnessapp.network.TokenRequest
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response

class FoodViewModel : ViewModel() {
    private val TAG = "ExerciseViewModel"

    private val _exercises = MutableLiveData<List<Food>>()
    val exercises: MutableLiveData<List<Food>> get() = _exercises
    fun fetchFood(token: String) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Fetching exercises")
                val response: Response<JsonObject> = RetrofitClient.apiService.getNutrition(
                    TokenRequest(token)
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Response successful: $response")
                    val body = response.body()
                    if (body != null) {
                        if (body.get("success").asBoolean) {
                            Log.d(TAG, "Parsing exercises")
                            val foodJson = body.getAsJsonArray("exercises")
                            val foodList = mutableListOf<Food>()
                            foodJson.forEach { foodElement ->
                                val foodObject = foodElement.asJsonObject
                                val foods = Food(
                                    userId = foodObject.get("userId").asInt,
                                    foodID = foodObject.get("FoodID").asInt,
                                    foodName = foodObject.get("FoodName").asString,
                                    caloriesPerServing = foodObject.get("CaloriesPerServing").asInt,
                                    proteinPerServing = foodObject.get("ProteinPerServing").asDouble,
                                    carbohydratesPerServing = foodObject.get("CarbohydratesPerServing").asDouble,
                                    fatPerServing = foodObject.get("FatPerServing").asDouble
                                )
                                foodList.add(foods)
                            }
                            _exercises.value = foodList
                            Log.d(TAG, "Exercises parsed: $foodList")
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
}