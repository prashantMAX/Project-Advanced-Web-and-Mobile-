package be.sharmaprashant.fitnessapp.model

data class DeleteNutritionRequest(
    val token: String,
    val food_id: Int
)

