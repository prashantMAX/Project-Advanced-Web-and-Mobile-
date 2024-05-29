package be.sharmaprashant.fitnessapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.data.Food
import be.sharmaprashant.fitnessapp.network.AddFoodRequest
import be.sharmaprashant.fitnessapp.network.ExercisesAndFoodRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FoodViewModel : ViewModel() {
    private val TAG = "FoodViewModel"

    private val _foods = MutableLiveData<List<Food>>()
    val food: MutableLiveData<List<Food>> get() = _foods

    var token: String? = null
    fun fetchFood(token: String, date: String) {
        this.token = token
        viewModelScope.launch {
            try {
                Log.d(TAG, "Fetching foods")
                val response: Response<JsonObject> = RetrofitClient.apiService.getNutrition(
                    ExercisesAndFoodRequest(token, date)
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Response successful: $response")
                    val body = response.body()
                    if (body != null) {
                        if (body.get("success").asBoolean) {
                            Log.d(TAG, "Parsing foods")
                            val foodJson = body.getAsJsonArray("foods")
                            val foodList = mutableListOf<Food>()
                            foodJson.forEach { foodElement ->
                                val foodObject = foodElement.asJsonObject
                                val foods = Food(
                                    userId = foodObject.get("userId").asInt,
                                    foodID = foodObject.get("FoodID").asInt,
                                    foodName = foodObject.get("FoodName").asString,
                                    caloriesPerServing = foodObject.get("CaloriesPerServing").asDouble,
                                    proteinPerServing = foodObject.get("ProteinPerServing").asDouble,
                                    carbohydratesPerServing = foodObject.get("CarbohydratesPerServing").asDouble,
                                    fatPerServing = foodObject.get("FatPerServing").asDouble,
                                    date = foodObject.get("date_id").asInt
                                )
                                Log.d(TAG, "Request: ${response.body()}")


                                foodList.add(foods)
                            }
                            _foods.value = foodList
                            Log.d(TAG, "foods parsed: $foodList")
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
    fun addFood(foodName: String, caloriesPerServing: Double, proteinPerServing: Double, carbohydratesPerServing: Double, fatPerServing: Double, date: String) {
        val token = this.token
        if (token == null) {
            Log.e(TAG, "Token is null, cannot add food")
            return
        }
        viewModelScope.launch {
            try {
                Log.d(TAG, "Adding food with token: $token")
                Log.d(TAG, "Adding food with date: $date")

                Log.d(TAG, "Adding food with food: $foodName")

                Log.d(TAG, "Adding food with calories: $caloriesPerServing")

                Log.d(TAG, "Adding food with protien: $proteinPerServing")

                Log.d(TAG, "Adding food with carb: $carbohydratesPerServing")
                Log.d(TAG, "Adding food with fat: $fatPerServing")
                Log.d(TAG, "Adding food with date: $date")

                val response = RetrofitClient.apiService.addFood(
                    AddFoodRequest(
                        token,
                        foodName,
                        caloriesPerServing,
                        proteinPerServing,
                        carbohydratesPerServing,
                        fatPerServing,
                        date
                    )
                )
                if (response.isSuccessful) {
                    Log.d(TAG, "Food added successfully")
                    fetchFood(token, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
                } else {
                    Log.e(TAG, "Failed to add food: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Exception when adding food: ${e.message}", e)
            }
        }
    }


}