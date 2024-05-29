package be.sharmaprashant.fitnessapp.ui

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddExerciseScreen(
    navController: NavHostController,
    viewModel: ExerciseViewModel = viewModel(),
    initialDate: String,
    onExerciseAdded: () -> Unit
) {
    val initialLocalDate = LocalDate.parse(initialDate, DateTimeFormatter.ISO_LOCAL_DATE)
    var exerciseDate by remember { mutableStateOf(initialLocalDate) }
    var exerciseName by remember { mutableStateOf("") }
    var caloriesPerRep by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            ShowDatePicker(exerciseDate) { selectedDate ->
                exerciseDate = selectedDate
            }
            Spacer(modifier = Modifier.height(24.dp))
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
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    navController.navigate("exercise")
                }) {
                    Text("Back")
                }
                Button(onClick = {
                    val caloriesPerRepDouble = caloriesPerRep.toDoubleOrNull()
                    if (caloriesPerRepDouble != null) {
                        viewModel.addExercises(
                            exerciseName,
                            caloriesPerRepDouble,
                            exerciseDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                        )
                        onExerciseAdded()
                        navController.navigate("exercise")
                    } else {
                        errorMessage = "Please enter a valid number for calories per rep"
                    }
                }) {
                    Text("Add Exercise")
                }
                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDatePicker(currentDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val newDate = LocalDate.of(year, month + 1, day)
                showDialog.value = false
                onDateSelected(newDate)
                println("New Date Selected: $newDate")
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        ).show()
    }

    Button(
        onClick = { showDialog.value = true },
        modifier = Modifier.fillMaxWidth().padding(25.dp),
        shape = RoundedCornerShape(2.dp)
    ) {
        Text("Select Date: ${currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddExerciseScreenPreview() {
    val navController = rememberNavController()
    val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    AddExerciseScreen(navController = navController, onExerciseAdded = {}, initialDate = today)
}
