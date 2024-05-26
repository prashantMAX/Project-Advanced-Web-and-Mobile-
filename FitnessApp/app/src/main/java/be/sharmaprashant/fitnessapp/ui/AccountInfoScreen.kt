package be.sharmaprashant.fitnessapp.ui


import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.data.UserProfile

@Composable
fun AccountInfoScreen(viewModel: AccountInfoViewModel = viewModel(), navController: NavHostController) {
    val userProfile = viewModel.userProfile

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "User Profile",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        ProfileItem(icon = Icons.Default.CoPresent, text = "UserID: ${userProfile.userID}")
        ProfileItem(icon = Icons.Default.Face, text = "Name: ${userProfile.name}")
        ProfileItem(icon = Icons.Default.FavoriteBorder, text = "Age: ${userProfile.age}")
        ProfileItem(icon = Icons.Default.MonitorWeight, text = "Weight: ${userProfile.weight} kg")
        ProfileItem(icon = Icons.Default.Height, text = "Height: ${userProfile.height} cm")
        ProfileItem(icon = Icons.Default.Male, text = "Gender: ${userProfile.gender}")
        ProfileItem(icon = Icons.Default.SportsGymnastics, text = "Activity Level: ${userProfile.activityLevel}")
        Box(){
            Button(onClick = {navController.navigate("home")
            }) {
                Text("Back", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
@Composable
fun ProfileItem(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Image(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = text)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewAccountInfo() {
    val navController = rememberNavController()
    AccountInfoScreen(navController = navController)
}


