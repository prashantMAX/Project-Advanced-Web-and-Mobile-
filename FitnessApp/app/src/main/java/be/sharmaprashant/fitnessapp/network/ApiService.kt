package be.sharmaprashant.fitnessapp.network

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

object RetrofitClient {
    private const val BASE_URL = "https://www.gauravghimire.be/API_Fitness/api/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

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

    @POST("TestAdd.php")
    @Headers("Content-Type: application/json")
    suspend fun addExercise(@Body request: AddExerciseRequest): Response<JsonObject>

    @POST("GetNutrition.php")
    @Headers("Content-Type: application/json")
    suspend fun getNutrition(@Body request: ExercisesAndFoodRequest): Response<JsonObject>
}

data class LoginResponse(
    val success: Boolean,
    val message: String?,
    val token: String?
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class TokenRequest(
    val token: String
)
data class ExercisesAndFoodRequest(
    val token: String,
    val date: String
)

data class AddExerciseRequest(
    val token: String,
    val exercise_name: String,
    val calories_per_rep: Double
)
