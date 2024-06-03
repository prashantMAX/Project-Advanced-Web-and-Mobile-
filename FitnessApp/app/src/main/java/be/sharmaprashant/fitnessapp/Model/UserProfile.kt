package be.sharmaprashant.fitnessapp.Model

data class UserProfile(
    val profileID: Int,
    val userID: Int,
    val name: String,
    val age: Int,
    val weight: Float,
    val height: Float,
    val gender: String,
    val activityLevel: String
)