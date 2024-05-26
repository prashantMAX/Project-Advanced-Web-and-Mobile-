package be.sharmaprashant.fitnessapp.ui

import androidx.lifecycle.ViewModel
import be.sharmaprashant.fitnessapp.data.UserProfile

class AccountInfoViewModel : ViewModel() {
    val userProfile: UserProfile = UserProfile(
        userID = 1,
        name = "XXX",
        age = 30,
        weight = 80.5f,
        height = 180.0f,
        gender = "Male",
        activityLevel = "Moderately Active"
    )
}