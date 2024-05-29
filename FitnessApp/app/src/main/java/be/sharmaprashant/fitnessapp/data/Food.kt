package be.sharmaprashant.fitnessapp.data

data class Food (
    val userId: Int,
    val foodID: Int,
    val foodName: String,
    val caloriesPerServing: Double,
    val proteinPerServing: Double,
    val carbohydratesPerServing: Double,
    val fatPerServing: Double,
    val date: Int,
)