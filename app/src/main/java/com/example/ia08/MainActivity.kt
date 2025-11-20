package com.example.ia08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ia08.ui.theme.IA08Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            IA08Theme {
                Scaffold(
                    bottomBar = {
                        DrawingToolToolbar(
                            currentColor = Color.Red,
                            onBrushSizeClick = { /* TODO */ },
                            onColorClick = { /* TODO */ },
                            onClearCanvas = { /* TODO */ }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        // TODO Canvas Implementation
                    }
                }
            }
        }
    }
}

@Composable
fun DrawingToolToolbar(
    currentColor: Color,
    onBrushSizeClick: () -> Unit,
    onColorClick: () -> Unit,
    onClearCanvas: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        tonalElevation = 3.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // Brush Size Icon
            IconButton(onClick = onBrushSizeClick) {
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = "Brush Size"
                )
            }

            // Brush Color Icon (Circle showing current color)
            IconButton(onClick = onColorClick) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(currentColor, shape = CircleShape)
                )
            }

            // Clear Canvas
            IconButton(onClick = onClearCanvas) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Clear Canvas"
                )
            }
        }
    }
}