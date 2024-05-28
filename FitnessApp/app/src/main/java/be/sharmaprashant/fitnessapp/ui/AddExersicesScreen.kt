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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.network.AddExerciseRequest
import be.sharmaprashant.fitnessapp.network.RetrofitClient
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel

@Composable
fun AddExerciseScreen(navController: NavHostController, viewModel: ExerciseViewModel = viewModel()) {
    var exerciseName by remember { mutableStateOf("") }
    var caloriesPerRep by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
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
            errorMessage?.let {
                Text(it, color = Color.Red)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(onClick = {
                val caloriesPerRepDouble = caloriesPerRep.toDouble()
                viewModel.addExercises(exerciseName,caloriesPerRepDouble)
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

