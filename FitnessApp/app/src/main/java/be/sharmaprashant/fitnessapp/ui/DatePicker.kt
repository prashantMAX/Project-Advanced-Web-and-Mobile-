package be.sharmaprashant.fitnessapp.ui

import android.app.DatePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDatePicker(currentDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val newDate = LocalDate.of(year, month + 1, day)
                showDialog.value = false
                onDateSelected(newDate)
                println("New Date Selected: $newDate")
            },
            currentDate.year,
            currentDate.monthValue - 1,
            currentDate.dayOfMonth
        ).show()
    }

    Button(
        onClick = { showDialog.value = true },
        modifier = Modifier.fillMaxWidth().padding(25.dp),
        shape = RoundedCornerShape(2.dp)
    ) {

            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Select Date: ${currentDate.format(DateTimeFormatter.ISO_LOCAL_DATE)}", style = MaterialTheme.typography.bodyLarge)

    }


}