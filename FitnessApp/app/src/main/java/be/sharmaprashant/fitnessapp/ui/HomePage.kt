package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
import be.sharmaprashant.fitnessapp.model.UserProfile

@Composable
fun HomePage(navController: NavHostController, userProfile: UserProfile) {
    PageBackground(
        title = stringResource(R.string.app_name),
        topBarImagePainter = painterResource(R.drawable.logo),
        backgroundImagePainter = painterResource(R.drawable.background),
        navController = navController,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(80.dp))
            ProfileSection(navController, userProfile)
            Spacer(Modifier.height(20.dp))
            MenuItems(navController)
        }
    }
}




@Composable
fun ProfileSection(navController: NavHostController, userProfile: UserProfile) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("accountInfo") }
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(userProfile.name, color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
fun MenuItems(navController: NavHostController) {
    val items = listOf("Home", "Exercise Planning", "Overview")
    LazyColumn {
        items(items) { item ->
            MenuItem(item, navController)
        }
    }
}

@Composable
fun MenuItem(item: String, navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToScreen(item, navController) }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(item, modifier = Modifier.weight(1f), color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge)
        Icon(Icons.Filled.ArrowForward, contentDescription = "Go to $item", tint = MaterialTheme.colorScheme.onPrimary)
    }
}


fun navigateToScreen(item: String, navController: NavHostController) {
    when (item) {
        "Home" -> navController.navigate("home")
        "Exercise Planning" -> navController.navigate("exercise")
        "Overview" -> navController.navigate("overview")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomePage() {
    val navController = rememberNavController()
    val userProfile = UserProfile(
        profileID = 1,
        userID = 1,
        name = "Test",
        age = 25,
        weight = 70.0f,
        height = 175.0f,
        gender = "Male",
        activityLevel = "Moderate"
    )
    HomePage(navController, userProfile)
}
