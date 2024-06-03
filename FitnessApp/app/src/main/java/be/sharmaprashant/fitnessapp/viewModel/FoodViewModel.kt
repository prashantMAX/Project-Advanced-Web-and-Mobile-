package be.sharmaprashant.fitnessapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.sharmaprashant.fitnessapp.model.Food
import be.sharmaprashant.fitnessapp.model.AddFoodRequest
import be.sharmaprashant.fitnessapp.model.DeleteNutritionRequest
import be.sharmaprashant.fitnessapp.model.ExercisesAndFoodRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class FoodViewModel : ViewModel() {
    private val _foods = MutableLiveData<List<Food>>()
    val food: MutableLiveData<List<Food>> get() = _foods

    var token: String? = null
    fun fetchFood(token: String, date: String) {
        this.token = token
        viewModelScope.launch {
            try {
                val response: Response<JsonObject> = RetrofitClient.apiService.getNutrition(
                    ExercisesAndFoodRequest(token, date)
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        if (body.get("success").asBoolean) {
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

                                foodList.add(foods)
                            }
                            _foods.value = foodList
                        } else {
                        }
                    } else {
                    }
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }
    fun addFood(foodName: String, caloriesPerServing: Double, proteinPerServing: Double, carbohydratesPerServing: Double, fatPerServing: Double, date: String) {
        val token = this.token
        if (token == null) {
            return
        }
        viewModelScope.launch {
            try {
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
                    fetchFood(token, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
                } else {
                }
            } catch (e: Exception) {
            }
        }
    }

    fun deleteFood(foodId: Int) {
        val token = this.token
        if (token == null) {
            return
        }
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.deleteNutrition(
                    DeleteNutritionRequest(
                        token,
                        foodId
                    )
                )
                if (response.isSuccessful) {
                    fetchFood(token, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()))
                } else {

                }
            } catch (e: Exception) {

            }
        }
    }



}