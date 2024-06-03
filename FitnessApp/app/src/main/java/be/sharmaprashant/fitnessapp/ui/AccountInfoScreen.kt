package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.MonitorWeight
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
import be.sharmaprashant.fitnessapp.model.UserProfile

@Composable
fun AccountInfoScreen(userProfile: UserProfile, navController: NavHostController) {
    PageBackground(
        title = stringResource(R.string.app_name),
        topBarImagePainter = painterResource(R.drawable.logo),
        backgroundImagePainter = painterResource(R.drawable.background),
        navController = navController
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "User Profile",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileItem(icon = Icons.Default.CoPresent, text = "UserID: ${userProfile.userID}")
            ProfileItem(icon = Icons.Default.Face, text = "Name: ${userProfile.name}")
            ProfileItem(icon = Icons.Default.FavoriteBorder, text = "Age: ${userProfile.age}")
            ProfileItem(icon = Icons.Default.MonitorWeight, text = "Weight: ${userProfile.weight} kg")
            ProfileItem(icon = Icons.Default.Height, text = "Height: ${userProfile.height} cm")
            ProfileItem(icon = Icons.Default.Male, text = "Gender: ${userProfile.gender}")
            ProfileItem(icon = Icons.Default.SportsGymnastics, text = "Activity Level: ${userProfile.activityLevel}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate("home")
            }) {
                Text("Back", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
fun ProfileItem(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAccountInfo() {
    val dummyUserProfile = UserProfile(
        profileID = 1,
        userID = 1,
        name = "John Doe",
        age = 30,
        weight = 80.5f,
        height = 180.0f,
        gender = "Male",
        activityLevel = "Moderately Active"
    )
    AccountInfoScreen(userProfile = dummyUserProfile, navController = rememberNavController())
}
