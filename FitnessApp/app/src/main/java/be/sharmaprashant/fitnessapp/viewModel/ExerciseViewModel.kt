package be.sharmaprashant.fitnessapp.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import be.sharmaprashant.fitnessapp.data.Exercises

class ExerciseViewModel:  ViewModel() {
    val exerciseList = mutableStateListOf(
        Exercises(exercise_id = 4, exercise_name = "Jumping Jacks", calories_per_rep = 0.10),
        Exercises(exercise_id = 5, exercise_name = "Burpees", calories_per_rep = 0.20),
        Exercises(exercise_id = 6, exercise_name = "Lunges", calories_per_rep = 0.60),
        Exercises(exercise_id = 7, exercise_name = "Plank", calories_per_rep = 0.15),
        Exercises(exercise_id = 8, exercise_name = "Bench Press", calories_per_rep = 100.00),
        Exercises(exercise_id = 9, exercise_name = "Test", calories_per_rep = 5.00)
    )

    fun addExercise(exercise_id: Int, exercise_name: String, calories_per_rep: Double) {
        val newExercise = Exercises(exercise_id = exercise_id, exercise_name = exercise_name, calories_per_rep = calories_per_rep)
        exerciseList.add(newExercise)
    }
}


