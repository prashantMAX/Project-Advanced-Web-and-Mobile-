package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.viewModel.FoodViewModel

@Composable
fun AddFoodScreen(
    navController: NavHostController,
    viewModel: FoodViewModel = viewModel(),
    onFoodAdded: () -> Unit
) {
    var foodName by remember { mutableStateOf("") }
    var caloriesPerServing by remember { mutableStateOf("") }
    var proteinPerServing by remember { mutableStateOf("") }
    var carbohydratesPerServing by remember { mutableStateOf("") }
    var fatPerServing by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = foodName,
                onValueChange = { foodName = it },
                label = { Text("Food Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = caloriesPerServing,
                onValueChange = { caloriesPerServing = it },
                label = { Text("Calories per Serving") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = proteinPerServing,
                onValueChange = { proteinPerServing = it },
                label = { Text("Protein per Serving") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = carbohydratesPerServing,
                onValueChange = { carbohydratesPerServing = it },
                label = { Text("Carbohydrates per Serving") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = fatPerServing,
                onValueChange = { fatPerServing = it },
                label = { Text("Fat per Serving") }
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                val calories = caloriesPerServing.toInt()
                val protein = proteinPerServing.toDouble()
                val carbs = carbohydratesPerServing.toDouble()
                val fat = fatPerServing.toDouble()
                if(calories != null || protein != null || carbs != null || fat != null) {
                    viewModel.addFood(foodName, calories, protein, carbs, fat)
                    onFoodAdded()
                    navController.navigate("home")
                }else{
                    errorMessage = "Please ensure all nutritional values are entered correctly"
                }
            }) {
                Text("Add Food")
            }
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
    val navController = rememberNavController()
    AddFoodScreen(navController = navController, onFoodAdded = {})
}
