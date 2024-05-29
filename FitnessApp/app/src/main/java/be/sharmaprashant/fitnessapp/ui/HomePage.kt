package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import be.sharmaprashant.fitnessapp.R
import be.sharmaprashant.fitnessapp.data.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController, userProfile: UserProfile) {
    Scaffold(
        topBar = {
            CustomTopAppBarWithImage(
                title = stringResource(R.string.app_name),
                backgroundImagePainter = painterResource(R.drawable.fitness)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
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
fun CustomTopAppBarWithImage(title: String, backgroundImagePainter: Painter) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // You can adjust this height based on your design needs
    ) {
        Image(
            painter = backgroundImagePainter,
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Filled.Logout,
                contentDescription = "Logout",
                tint = MaterialTheme.colorScheme.onPrimary
            )
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
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(userProfile.name)

    }
}

@Composable
fun MenuItems(navController: NavHostController) {
    val items = listOf("Home", "Exercise Planning")
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
        Text(item, modifier = Modifier.weight(1f))
        Icon(Icons.Filled.ArrowForward, contentDescription = "Go to $item")
    }
}

fun navigateToScreen(item: String, navController: NavHostController) {
    when (item) {
        "Home" -> navController.navigate("home")
        "Exercise Planning" -> navController.navigate("exercise")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomePage() {
    val navController = rememberNavController()
    val userProfile = UserProfile(
        profileID = 1,
        userID = 1,
        name = "Sharmaprashant",
        age = 25,
        weight = 70.0f,
        height = 175.0f,
        gender = "Male",
        activityLevel = "Moderate"
    )
    HomePage(navController, userProfile)
}
