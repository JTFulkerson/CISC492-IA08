package com.example.ia08.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToolPanel(
    brushSize: Float,
    currentColor: Color,
    onBrushSizeChange: (Float) -> Unit,
    onColorChange: (Color) -> Unit,
    onClearCanvas: () -> Unit,
    onUndoLastStroke: () -> Unit
) {
    val brushSizes = listOf(4f, 8f, 16f, 32f)
    val availableColors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow)

    var showBrushMenu by remember { mutableStateOf(false) }
    var showColorMenu by remember { mutableStateOf(false) }

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
            // Brush Icon
            Box {
                IconButton(onClick = {
                    showBrushMenu = !showBrushMenu
                    showColorMenu = false
                }) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = "Brush Options"
                    )
                }

                DropdownMenu(
                    expanded = showBrushMenu,
                    onDismissRequest = { showBrushMenu = false }
                ) {
                    brushSizes.forEach { size ->
                        DropdownMenuItem(
                            text = {
                                Box(
                                    modifier = Modifier
                                        .size(size.dp)
                                        .background(currentColor, CircleShape)
                                        .border(
                                            width = if (size == brushSize) 2.dp else 0.dp,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            shape = CircleShape
                                        )
                                )
                            },
                            onClick = {
                                onBrushSizeChange(size)
                                showBrushMenu = false
                            }
                        )
                    }
                }
            }

            // Color Circle
            Box {
                IconButton(onClick = {
                    showColorMenu = !showColorMenu
                    showBrushMenu = false
                }) {
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .background(currentColor, CircleShape)
                            .border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                    )
                }

                DropdownMenu(
                    expanded = showColorMenu,
                    onDismissRequest = { showColorMenu = false }
                ) {
                    availableColors.forEach { color ->
                        DropdownMenuItem(
                            text = {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(color, CircleShape)
                                        .border(
                                            width = if (color == currentColor) 2.dp else 0.dp,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            shape = CircleShape
                                        )
                                )
                            },
                            onClick = {
                                onColorChange(color)
                                showColorMenu = false
                            }
                        )
                    }
                }
            }

            IconButton(onClick = onUndoLastStroke) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Undo Last Stroke"
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
