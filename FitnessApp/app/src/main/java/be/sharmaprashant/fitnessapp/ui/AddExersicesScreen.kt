package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel

@Composable
fun AddExerciseScreen(navController: NavHostController, viewModel: ExerciseViewModel = viewModel()) {
    var exerciseName by remember { mutableStateOf("") }
    var caloriesPerRep by remember { mutableStateOf("") }
    var exerciseId by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {  // This Box centers its children
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,  // Center children horizontally
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)  // Provide some padding around the column
        ) {
            TextField(
                value = exerciseId,
                onValueChange = { exerciseId = it },
                label = { Text("Exercise ID") }
            )
            Spacer(modifier = Modifier.height(16.dp))  // Space between TextField components
            TextField(
                value = exerciseName,
                onValueChange = { exerciseName = it },
                label = { Text("Exercise Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = caloriesPerRep,
                onValueChange = { caloriesPerRep = it },
                label = { Text("Calories per Rep") }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                // Ensure safe conversion from String to required data types
                val id = exerciseId.toIntOrNull() ?: 0
                val calories = caloriesPerRep.toDoubleOrNull() ?: 0.0
                viewModel.addExercise(id, exerciseName, calories)
            }) {
                Text("Add Exercise")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddExerciseScreenPreview() {
    val navController = rememberNavController()
    AddExerciseScreen(navController = navController)
}

