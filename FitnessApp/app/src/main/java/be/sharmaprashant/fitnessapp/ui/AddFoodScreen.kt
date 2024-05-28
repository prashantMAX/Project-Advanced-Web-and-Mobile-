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
import be.sharmaprashant.fitnessapp.viewModel.FoodViewModel

@Composable
fun AddFoodScreen(navController: NavHostController, viewModel: FoodViewModel = viewModel()) {
    var foodName by remember { mutableStateOf("") }
    var caloriesPerServing by remember { mutableStateOf("") }
    var protienPerServing by remember { mutableStateOf("") }
    var carbohyfratesPerServing by remember { mutableStateOf("") }
    var fatPerServing by remember { mutableStateOf("") }
    var exerciseId by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {  // This Box centers its children
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,  // Center children horizontally
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)  // Provide some padding around the column
        ) {

            Spacer(modifier = Modifier.height(16.dp))  // Space between TextField components
            TextField(
                value = foodName,
                onValueChange = { foodName = it },
                label = { Text("Exercise Name") }
            )

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = caloriesPerServing,
                onValueChange = { caloriesPerServing = it },
                label = { Text("Calories per Rep") }
            )

            TextField(
                value = protienPerServing,
                onValueChange = { protienPerServing = it },
                label = { Text("protien per Rep") }
            )

            TextField(
                value = carbohyfratesPerServing,
                onValueChange = { carbohyfratesPerServing = it },
                label = { Text("carbohyfrates per Rep") }
            )

            TextField(
                value = fatPerServing,
                onValueChange = { fatPerServing = it },
                label = { Text("fat per Rep") }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {
                navController.navigate("home")
            }) {
                Text("Add Exercise")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddFoodScreenPreview() {
    val navController = rememberNavController()
    AddFoodScreen(navController = navController)
}

