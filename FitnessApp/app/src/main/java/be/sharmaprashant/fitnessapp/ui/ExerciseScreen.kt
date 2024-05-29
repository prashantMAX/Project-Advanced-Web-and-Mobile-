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

    Scaffold(
        topBar = {
            CustomTopAppBarWithImage(
                title = stringResource(R.string.app_name),
                backgroundImagePainter = painterResource(R.drawable.backgrounf)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.backgroundd),
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(3.dp),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
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
                        Text(formattedDate, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Button(onClick = { }) {
                            Text("Rest day?", fontSize = 18.sp, fontWeight = FontWeight.Bold)
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
                            Text("Exercises", fontSize = 18.sp, color = Color.White, modifier = Modifier)
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
                                .border(2.dp, Color.White)
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
                            Text("Food", fontSize = 18.sp, color = Color.White, modifier = Modifier)
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
                                .border(2.dp, Color.White)
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
                            Text("Previous Day", color = Color.White, fontSize = 18.sp)
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
                            Text("Next Day", color = Color.White, fontSize = 18.sp)
                        }
                    }
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
        Text(text = exercise.exercise_name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Calories per rep: ${exercise.calories_per_rep}", fontSize = 14.sp, color = Color.White)
    }
}

@Composable
fun FoodItem(food: Food) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = food.foodName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Calories per serving: ${food.caloriesPerServing}", fontSize = 14.sp, color = Color.White)
        Text(text = "Protein per serving: ${food.proteinPerServing}", fontSize = 14.sp, color = Color.White)
        Text(text = "Carbohydrates per serving: ${food.carbohydratesPerServing}", fontSize = 14.sp, color = Color.White)
        Text(text = "Fat per serving: ${food.fatPerServing}", fontSize = 14.sp, color = Color.White)
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
