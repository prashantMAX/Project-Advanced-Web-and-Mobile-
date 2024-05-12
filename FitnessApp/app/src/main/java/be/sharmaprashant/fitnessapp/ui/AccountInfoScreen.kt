import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import be.sharmaprashant.fitnessapp.data.UserProfile

@Composable
fun AccountInfoScreen() {
    // Dummy user profile data for demonstration
    val userProfile = UserProfile(
        userID = 1,
        name = "John Doe",
        age = 30,
        weight = 75F,
        height = 180.0F,
        gender = "Male",
        activityLevel = "Active"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AccountInfo(userProfile)
    }
}

@Composable
fun AccountInfo(userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User Profile")
        Spacer(modifier = Modifier.height(16.dp))
        ProfileItem(label = "UserID:", value = userProfile.userID.toString())
        ProfileItem(label = "Name:", value = userProfile.name)
        ProfileItem(label = "Age:", value = userProfile.age.toString())
        ProfileItem(label = "Weight:", value = "${userProfile.weight} kg")
        ProfileItem(label = "Height:", value = "${userProfile.height} cm")
        ProfileItem(label = "Gender:", value = userProfile.gender)
        ProfileItem(label = "Activity Level:", value = userProfile.activityLevel)
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column {
        Text(text = "$label $value")
    }
}
