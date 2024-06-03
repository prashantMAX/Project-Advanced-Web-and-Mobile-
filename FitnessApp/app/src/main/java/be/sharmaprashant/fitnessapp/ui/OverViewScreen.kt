package be.sharmaprashant.fitnessapp.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
import be.sharmaprashant.fitnessapp.model.Exercises
import be.sharmaprashant.fitnessapp.viewModel.ExerciseViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OverviewScreen(
    navController: NavHostController,
    exercise: List<Exercises>,
    exerciseviewModel: ExerciseViewModel = viewModel()
) {
    var currentDay by remember { mutableStateOf(LocalDate.now()) }
    val formattedDate = currentDay.format(DateTimeFormatter.ofPattern("EEEE-MM-dd"))
    var clickedDate by remember { mutableStateOf(LocalDate.now()) }

    PageBackground(
        title = stringResource(R.string.app_name),
        topBarImagePainter = painterResource(R.drawable.logo),
        backgroundImagePainter = painterResource(R.drawable.background),
        navController = navController
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(8.dp)
                .fillMaxSize()
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            val dateList = mutableListOf<LocalDate>()
            for (i in -3..3) {
                dateList.add(currentDay.plusDays(i.toLong()))
            }
            items(dateList) { date ->
                var expanded by remember { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioNoBouncy,
                                stiffness = Spring.StiffnessMedium
                            )
                        )
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = date.format(DateTimeFormatter.ofPattern("EEEE")),
                            style = MaterialTheme.typography.titleLarge,
                            color = if (date == currentDay) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.onPrimary
                        )// Increased font size for dates
                        Spacer(modifier = Modifier.weight(1f))
                        ExpandItem(
                            expanded = expanded,
                            onClick = {
                                clickedDate = date // Update clickedDate when item is clicked
                                exerciseviewModel.token?.let {
                                    exerciseviewModel.fetchExercises(it, clickedDate.toString())
                                }
                                expanded = !expanded
                            }
                        )
                    }

                    if (expanded) {
                        if (exercise.isEmpty()) {
                            Text(text = "Rest Day", modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            ExerciseList(exercise = exercise, height = 200.dp)
                        }
                    }
                }
            }
        }
    }
}



@Composable
fun ExerciseList(exercise: List<Exercises>, height: Dp) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
    ) {
        items(exercise) { exercise ->
            ExerciseOverview(exercise = exercise)
        }
    }
}





@Composable
fun ExerciseOverview(exercise: Exercises) {
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
private fun ExpandItem(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = "expand",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    val navController = rememberNavController()
    val sampleExercises = listOf(
        Exercises(1, 1, "Push Up", 0.5, 2),
        Exercises(1, 2, "Sit Up", 0.4, 2),
        Exercises(1, 3, "Squat", 0.6, 2)
    )
    OverviewScreen(navController = navController, exercise = sampleExercises)
}