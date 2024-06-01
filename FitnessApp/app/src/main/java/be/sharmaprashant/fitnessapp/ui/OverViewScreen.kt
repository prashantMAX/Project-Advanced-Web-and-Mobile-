package be.sharmaprashant.fitnessapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import be.sharmaprashant.fitnessapp.viewModel.FoodViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OverviewScreen(
    navController: NavHostController,
    exercise: List<Exercises>,
    exerciseviewModel: ExerciseViewModel = viewModel(),
    foodviewModel: FoodViewModel = viewModel()
) {
    var currentDay by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate = currentDay.format(DateTimeFormatter.ofPattern("EEEE-MM-dd"))

    PageBackground(
        title = stringResource(R.string.app_name),
        topBarImagePainer = painterResource(R.drawable.fitness),
        backgroundImagePainter = null,
        navController = navController
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Date",
                    fontSize = 36.sp, // Increased font size for "Date"
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Done?",
                    fontSize = 36.sp, // Increased font size for "Done?"
                    fontWeight = FontWeight.Bold
                )
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
                val dateList = mutableListOf<LocalDate>()
                for (i in -3..3) {
                    dateList.add(currentDay.plusDays(i.toLong()))
                }
                items(dateList) { date ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Handle date click here */ }
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = date.format(DateTimeFormatter.ofPattern("EEE")), fontSize = 36.sp) // Increased font size for dates
                        Spacer(modifier = Modifier.weight(1f))
                        val isChecked = // Logic to check if exercise is done for this date
                            false // Replace with actual logic
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { /* Update exercise completion status */ },
                            modifier = Modifier.size(24.dp) // Increased size for checkbox
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
fun OverviewScreenPreview() {
    val navController = rememberNavController() // Simulate navigation controller
    OverviewScreen(navController = navController, exercise = emptyList())
}