package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun PageBackground(
    title: String,
    topBarImagePainer: Painter,
    backgroundImagePainter: Painter?,
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                Box(
                    modifier = Modifier.height(100.dp)
                ) {
                    CustomTopAppBarWithImage(
                        title = title,
                        backgroundImagePainter = topBarImagePainer,
                        navController = navController
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(onClick = { navController.navigate("home") }) {
                        Text("Home")
                    }
                    Button(onClick = { navController.navigate("exercise") }) {
                        Text("Exercise")
                    }
                    Button(onClick = { navController.navigate("overview") }) {
                        Text("Overview")
                    }
                }
            }
        }
    ) { innerPadding ->
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(3.dp, MaterialTheme.colorScheme.onSurface)
        ) {
            if (backgroundImagePainter != null) {
                Image(
                    painter = backgroundImagePainter,
                    contentDescription = "Background Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            ) {
                content(innerPadding)
            }
        }
    }
}

@Composable
fun CustomTopAppBarWithImage(title: String, backgroundImagePainter: Painter, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .border(3.dp, MaterialTheme.colorScheme.onSurface)
    ) {
        Image(
            painter = backgroundImagePainter,
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 16.dp)
                    .clickable { navController.navigate("home") },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
