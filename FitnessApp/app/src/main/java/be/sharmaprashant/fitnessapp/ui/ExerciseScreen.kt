package be.sharmaprashant.fitnessapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
import be.sharmaprashant.fitnessapp.data.Exercises
import be.sharmaprashant.fitnessapp.data.Food
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import be.sharmaprashant.fitnessapp.viewModel.FoodViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseScreen(navController: NavHostController, exercise: List<Exercises>, food: List<Food>, exerciseviewModel: ExerciseViewModel = viewModel(), foodviewModel: FoodViewModel = viewModel()) {
    var currentDay by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate = currentDay.format(DateTimeFormatter.ofPattern("EEEE-MM-dd"))

    PageBackground(
        title = stringResource(R.string.app_name),
        topBarImagePainer = painterResource(R.drawable.fitness),
        backgroundImagePainter = painterResource(R.drawable.test),
        navController = navController
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formattedDate, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
                Button(onClick = { }) {
                    Text("Rest day?", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Exercises", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(modifier = Modifier.weight(1f))  // Make space between text and button
                    Button(onClick = {
                        navController.navigate("addExerciseScreen/${currentDay.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Exercise")
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp)
                        .shadow(20.dp, shape = MaterialTheme.shapes.small)
                        .border(2.dp, MaterialTheme.colorScheme.onPrimary, shape = MaterialTheme.shapes.large)
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    items(exercise) { exercise ->
                        ExerciseItem(exercise = exercise)
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Food", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
                    Spacer(modifier = Modifier.weight(1f))  // Make space between text and button
                    Button(onClick = {
                        navController.navigate("AddFoodScreen/${currentDay.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Food")
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(1.dp)
                        .shadow(20.dp, shape = MaterialTheme.shapes.small)
                        .border(2.dp, MaterialTheme.colorScheme.onPrimary, shape = MaterialTheme.shapes.large)
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    items(food) { food ->
                        FoodItem(food = food)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        currentDay = currentDay.minusDays(1)
                        exerciseviewModel.token?.let {
                            exerciseviewModel.fetchExercises(it, currentDay.toString())
                        }
                        foodviewModel.token?.let {
                            foodviewModel.fetchFood(it, currentDay.toString())
                        }
                    }
                ) {
                    Text("Previous Day", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
                }

                Button(
                    onClick = {
                        currentDay = currentDay.plusDays(1)
                        exerciseviewModel.token?.let {
                            exerciseviewModel.fetchExercises(it, currentDay.toString())
                        }
                        foodviewModel.token?.let {
                            foodviewModel.fetchFood(it, currentDay.toString())
                        }
                    }
                ) {
                    Text("Next Day", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
                }
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
        Text(text = exercise.exercise_name, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Calories per rep: ${exercise.calories_per_rep}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
fun FoodItem(food: Food) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = food.foodName, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onPrimary)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Calories per serving: ${food.caloriesPerServing}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
        Text(text = "Protein per serving: ${food.proteinPerServing}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
        Text(text = "Carbohydrates per serving: ${food.carbohydratesPerServing}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
        Text(text = "Fat per serving: ${food.fatPerServing}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onPrimary)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ExerciseScreenPreview() {
    val navController = rememberNavController()
    val sampleExercises = listOf(
        Exercises(1, 1, "Push Up", 0.5, 2),
        Exercises(1, 2, "Sit Up", 0.4,2),
        Exercises(1, 3, "Squat", 0.6,2)
    )
    val dummyFoods = listOf(
        Food(userId=1, foodID = 2, foodName = "Apple", caloriesPerServing = 52.0, proteinPerServing = 0.3, carbohydratesPerServing = 13.8, fatPerServing = 0.4, date = 2),
        Food(userId=3, foodID = 4, foodName = "Apple", caloriesPerServing = 52.0, proteinPerServing = 0.3, carbohydratesPerServing = 13.8, fatPerServing = 0.4, date = 2)
    )
    ExerciseScreen(navController = navController, exercise = sampleExercises, food = dummyFoods)
}
