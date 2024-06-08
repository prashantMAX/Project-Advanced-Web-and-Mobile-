package be.sharmaprashant.fitnessapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
import be.sharmaprashant.fitnessapp.model.Exercises
import be.sharmaprashant.fitnessapp.model.Food
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import be.sharmaprashant.fitnessapp.viewModel.FoodViewModel
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExerciseScreen(
    navController: NavHostController,
    exerciseViewModel: ExerciseViewModel = viewModel(),
    foodViewModel: FoodViewModel = viewModel()
) {
    val currentDay by exerciseViewModel.currentDay
    val formattedDate = currentDay.format(DateTimeFormatter.ofPattern("EEEE-MM-dd"))


    // elk keer
    foodViewModel.token?.let {
        foodViewModel.fetchFood(it, exerciseViewModel.currentDay.value.toString())
    }

    exerciseViewModel.token?.let {
        exerciseViewModel.fetchExercises(it, exerciseViewModel.currentDay.value.toString())
    }

    PageBackground(
        title = stringResource(R.string.app_name),
        topBarImagePainter = painterResource(R.drawable.logo),
        backgroundImagePainter = painterResource(R.drawable.background),
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

            }

            // Exercises Section
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
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        navController.navigate("addExerciseScreen/${currentDay.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Exercise")
                    }
                }
                val exercises by exerciseViewModel.exercises.observeAsState(emptyList())
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .border(2.dp, MaterialTheme.colorScheme.onPrimary, shape = MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    items(exercises) { exercise ->
                        ExerciseItem(exercise = exercise) {
                            exerciseViewModel.deleteExercise(exercise.exercise_id)
                        }
                    }
                }
            }

            // Food Section
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
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        navController.navigate("AddFoodScreen/${currentDay.format(DateTimeFormatter.ISO_LOCAL_DATE)}")
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Food")
                    }
                }
                val foods by foodViewModel.food.observeAsState(emptyList())
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .border(2.dp, MaterialTheme.colorScheme.onPrimary, shape = MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
                        .padding(10.dp)
                        .fillMaxWidth()
                ) {
                    items(foods) { food ->
                        FoodItem(food = food) {
                            foodViewModel.deleteFood(food.foodID)
                        }
                    }
                }
            }

            // Navigation Row for Previous and Next Day
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        exerciseViewModel.changeDay(-1)

                        exerciseViewModel.token?.let {
                            exerciseViewModel.fetchExercises(it, exerciseViewModel.currentDay.value.toString())
                        }
                        foodViewModel.token?.let {
                            foodViewModel.fetchFood(it, exerciseViewModel.currentDay.value.toString())
                        }
                    }
                ) {
                    Text("Previous Day", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimary)
                }

                Button(
                    onClick = {
                        exerciseViewModel.changeDay(1)

                        exerciseViewModel.token?.let {
                            exerciseViewModel.fetchExercises(it, exerciseViewModel.currentDay.value.toString())
                        }


                        foodViewModel.token?.let {
                            foodViewModel.fetchFood(it, exerciseViewModel.currentDay.value.toString())
                        }
                    }
                ) {
                    Text("Next Day", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}


@Composable
fun ExerciseItem(exercise: Exercises, onDeleteClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = exercise.exercise_name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            IconButton(
                onClick = onDeleteClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Exercise" ,
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Calories per rep: ${exercise.calories_per_rep}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun FoodItem(food: Food, onDeleteClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = food.foodName,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
            IconButton(
                onClick = onDeleteClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Food",
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Calories per serving: ${food.caloriesPerServing}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Protein per serving: ${food.proteinPerServing}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Carbohydrates per serving: ${food.carbohydratesPerServing}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Fat per serving: ${food.fatPerServing}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ExerciseScreenPreview() {
    val navController = rememberNavController()
    val sampleExercises = listOf(
        Exercises(1, 1, "Push Up", 0.5, 2),
        Exercises(1, 2, "Sit Up", 0.4, 2),
        Exercises(1, 3, "Squat", 0.6, 2)
    )
    val dummyFoods = listOf(
        Food(userId = 1, foodID = 2, foodName = "Apple", caloriesPerServing = 52.0, proteinPerServing = 0.3, carbohydratesPerServing = 13.8, fatPerServing = 0.4, date = 2),
        Food(userId = 3, foodID = 4, foodName = "Banana", caloriesPerServing = 89.0, proteinPerServing = 1.1, carbohydratesPerServing = 22.8, fatPerServing = 0.3, date = 2)
    )
    ExerciseScreen(navController = navController)
}
