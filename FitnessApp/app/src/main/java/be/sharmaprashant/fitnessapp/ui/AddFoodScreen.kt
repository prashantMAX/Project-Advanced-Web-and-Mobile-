package be.sharmaprashant.fitnessapp.ui

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
import be.sharmaprashant.fitnessapp.viewModel.FoodViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddFoodScreen(
    navController: NavHostController,
    viewModel: FoodViewModel = viewModel(),
    initialDate: String,
    onFoodAdded: () -> Unit
) {
    val initialLocalDate = LocalDate.parse(initialDate, DateTimeFormatter.ISO_LOCAL_DATE)
    var foodDate by remember { mutableStateOf(initialLocalDate) }
    var foodName by remember { mutableStateOf("") }
    var caloriesPerServing by remember { mutableStateOf("") }
    var proteinPerServing by remember { mutableStateOf("") }
    var carbohydratesPerServing by remember { mutableStateOf("") }
    var fatPerServing by remember { mutableStateOf("") }
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
                ShowDatePicker(foodDate) { selectedDate ->
                    foodDate = selectedDate
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    label = { Text("Food Name", style = MaterialTheme.typography.bodyMedium) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = caloriesPerServing,
                    onValueChange = { caloriesPerServing = it },
                    label = {
                        Text(
                            "Calories per Serving",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = proteinPerServing,
                    onValueChange = { proteinPerServing = it },
                    label = {
                        Text(
                            "Protein per Serving",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = carbohydratesPerServing,
                    onValueChange = { carbohydratesPerServing = it },
                    label = {
                        Text(
                            "Carbohydrates per Serving",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = fatPerServing,
                    onValueChange = { fatPerServing = it },
                    label = { Text("Fat per Serving", style = MaterialTheme.typography.bodyMedium) }
                )
                Spacer(modifier = Modifier.height(24.dp))

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
                            val calories = caloriesPerServing.toDoubleOrNull()
                            val protein = proteinPerServing.toDoubleOrNull()
                            val carbs = carbohydratesPerServing.toDoubleOrNull()
                            val fat = fatPerServing.toDoubleOrNull()
                            if (calories != null) {
                                if (protein != null) {
                                    if (carbs != null) {
                                        if (fat != null) {
                                            viewModel.addFood(
                                                foodName,
                                                calories,
                                                protein,
                                                carbs,
                                                fat,
                                                foodDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
                                            )
                                        }
                                    }
                                }
                                onFoodAdded()
                                navController.navigate("exercise")
                            } else {
                                errorMessage =
                                    "Please ensure all nutritional values are entered correctly"
                            }
                        }) {
                            Text("Add Food", style = MaterialTheme.typography.bodyMedium)
                        }

                    }
                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}





@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
    val navController = rememberNavController()
    val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    AddFoodScreen(navController = navController, onFoodAdded = {}, initialDate = today)
}
