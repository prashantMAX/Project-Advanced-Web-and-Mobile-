package be.sharmaprashant.fitnessapp.model

data class DeleteExerciseRequest(
    val token: String,
    val exercise_id: Int
)
