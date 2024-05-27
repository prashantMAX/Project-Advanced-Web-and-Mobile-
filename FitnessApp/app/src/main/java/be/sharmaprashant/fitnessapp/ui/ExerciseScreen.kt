package be.sharmaprashant.fitnessapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseScreen(navController: NavHostController, exercise: List<Exercises>) {
    var currentDay by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate = currentDay.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM"))

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formattedDate, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Button(onClick = { }) {
                Text("Rest day?", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }

        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Oefeningen", fontSize = 18.sp, modifier = Modifier.border(1.dp, Color.Black))
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { navController.navigate("addExerciseScreen") }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn {
                items(exercise) { exercise ->
                    ExerciseItem(exercise = exercise)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Food", fontSize = 18.sp, modifier = Modifier.border(1.dp, Color.Black))
            Text("Extra", fontSize = 18.sp, modifier = Modifier.border(1.dp, Color.Black))
        }

        Box(
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
        ) {
            Button(onClick = {
                currentDay = currentDay.plusDays(1)
            }) {
                Text("Volgende dag", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercises) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = exercise.exercise_name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Calories per rep: ${exercise.calories_per_rep}", fontSize = 14.sp)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ExerciseScreenPreview() {
    val navController = rememberNavController()
    val sampleExercises = listOf(
        Exercises(1, 1, "Push Up", 0.5),
        Exercises(1, 2, "Sit Up", 0.4),
        Exercises(1, 3, "Squat", 0.6)
    )
    ExerciseScreen(navController = navController, exercise = sampleExercises)
}