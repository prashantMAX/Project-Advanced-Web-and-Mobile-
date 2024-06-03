package be.sharmaprashant.fitnessapp.model

data class AddFoodRequest(
    val token: String,
    val food_name: String,
    val calories_per_serving: Double,
    val protein_per_serving: Double,
    val carbohydrates_per_serving: Double,
    val fat_per_serving: Double,
    val date: String
)

