package be.sharmaprashant.fitnessapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
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
                    label = { Text("Exercise Name", style = MaterialTheme.typography.bodyMedium) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = caloriesPerRep,
                    onValueChange = { caloriesPerRep = it },
                    label = { Text("Calories per Rep", style = MaterialTheme.typography.bodyMedium) }
                )
                Spacer(modifier = Modifier.height(35.dp))
                Column {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = {
                            navController.navigate("exercise")
                        }) {
                            Text("Back", style = MaterialTheme.typography.bodyMedium)
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
                            Text("Add Exercise", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it,
                            Modifier.background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
                            color = MaterialTheme.colorScheme.error
                        )

                    }
                }
            }
        }
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
