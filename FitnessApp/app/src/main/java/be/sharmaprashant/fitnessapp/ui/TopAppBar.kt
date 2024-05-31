package be.sharmaprashant.fitnessapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun PageBackground(
    title: String,
    topBarImagePainer: Painter,
    backgroundImagePainter: Painter,
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
                        backgroundImagePainter = topBarImagePainer
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
            Image(
                painter = backgroundImagePainter,
                contentDescription = "Background Image",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
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
fun CustomTopAppBarWithImage(title: String, backgroundImagePainter: Painter) {
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
                    .padding(horizontal = 16.dp),
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
