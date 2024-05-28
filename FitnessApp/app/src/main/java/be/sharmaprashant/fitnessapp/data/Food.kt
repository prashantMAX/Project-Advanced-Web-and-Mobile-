package be.sharmaprashant.fitnessapp.data

data class Food (
    val userId: Int,
    val foodID: Int,
    val foodName: String,
    val caloriesPerServing: Int,
    val proteinPerServing: Double,
    val carbohydratesPerServing: Double,
    val fatPerServing: Double,
    )