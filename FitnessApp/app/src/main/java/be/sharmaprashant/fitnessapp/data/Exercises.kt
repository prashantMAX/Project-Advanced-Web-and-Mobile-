package be.sharmaprashant.fitnessapp.data

import android.icu.text.DecimalFormat

data class Exercises(
    val exercise_id: Int,
    val exercise_name: String,
    val calories_per_rep: Double,
)
