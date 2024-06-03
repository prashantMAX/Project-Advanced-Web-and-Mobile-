package be.sharmaprashant.fitnessapp.model

data class AddExerciseRequest(
    val token: String,
    val exercise_name: String,
    val calories_per_rep: Double,
    val date: String
)

