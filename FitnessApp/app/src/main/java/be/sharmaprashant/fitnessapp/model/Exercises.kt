package be.sharmaprashant.fitnessapp.model

data class Exercises(
    val userId: Int,
    val exercise_id: Int,
    val exercise_name: String,
    val calories_per_rep: Double,
    val date: Int,
)
