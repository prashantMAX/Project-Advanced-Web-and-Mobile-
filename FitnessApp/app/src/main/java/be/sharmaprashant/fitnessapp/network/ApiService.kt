package be.sharmaprashant.fitnessapp.network

import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.data.UserProfile
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("GetUser.php")
    suspend fun getUserData(@Header("Authorization") token: String): Response<UserProfile>

    @GET("GetExercises.php")
    suspend fun getExercise(@Header("Authorization") token: String): Response<List<Exercises>>
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

