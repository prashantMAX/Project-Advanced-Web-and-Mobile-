package be.sharmaprashant.fitnessapp.network

import be.sharmaprashant.fitnessapp.model.AddExerciseRequest
import be.sharmaprashant.fitnessapp.model.AddFoodRequest
import be.sharmaprashant.fitnessapp.model.DeleteExerciseRequest
import be.sharmaprashant.fitnessapp.model.DeleteNutritionRequest
import be.sharmaprashant.fitnessapp.model.ExercisesAndFoodRequest
import be.sharmaprashant.fitnessapp.model.ExercisesPerDateRequest
import be.sharmaprashant.fitnessapp.model.LoginRequest
import be.sharmaprashant.fitnessapp.model.TokenRequest
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://www.gauravghimire.be/API_Fitness/api/"

private val retrofit = Retrofit.Builder()
         .addConverterFactory(GsonConverterFactory.create())
         .baseUrl(BASE_URL)
         .build()

interface ApiService {
    @POST("Login.php")
    @Headers("Content-Type: application/json")
    suspend fun login(@Body request: LoginRequest): Response<JsonObject>

    @POST("GetUser.php")
    @Headers("Content-Type: application/json")
    suspend fun getUserData(@Body request: TokenRequest): Response<JsonObject>

    @POST("GetExercises.php")
    @Headers("Content-Type: application/json")
    suspend fun getExercise(@Body request: ExercisesAndFoodRequest): Response<JsonObject>

    @POST("AddExercises.php")
    @Headers("Content-Type: application/json")
    suspend fun addExercise(@Body request: AddExerciseRequest): Response<JsonObject>

    @POST("AddNutrition.php")
    @Headers("Content-Type: application/json")
    suspend fun addFood(@Body request: AddFoodRequest): Response<JsonObject>

    @POST("GetNutrition.php")
    @Headers("Content-Type: application/json")
    suspend fun getNutrition(@Body request: ExercisesAndFoodRequest): Response<JsonObject>

    @POST("Test.php")
    @Headers("Content-Type: application/json")
    suspend fun getExercisesPerDate(@Body request: ExercisesPerDateRequest): Response<JsonObject>

    @POST("deleteNutrition.php")
    @Headers("Content-Type: application/json")
    suspend fun deleteNutrition(@Body request: DeleteNutritionRequest): Response<JsonObject>

    @POST("deleteExercise.php")
    @Headers("Content-Type: application/json")
    suspend fun deleteExercise(@Body request: DeleteExerciseRequest): Response<JsonObject>

}

object RetrofitClient {
    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}