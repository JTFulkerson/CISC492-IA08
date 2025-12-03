package com.example.ia08.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp

data class StrokePath(
    val points: List<Offset>,
    val color: Color,
    val strokeWidth: Dp
)

@Composable
fun DrawingCanvas(
    brushColor: Color,
    brushSize: Dp,
    clearCanvasSignal: Boolean,
    onCanvasCleared: () -> Unit,
    undoLastStrokeSignal: Boolean,
    onUndoHandled: () -> Unit,
    canvasBackgroundColor: Color,
    modifier: Modifier = Modifier
) {
    // Persistent strokes
    val strokes = remember { mutableStateListOf<StrokePath>() }

    // Current in-progress stroke points
    var currentPoints by remember { mutableStateOf<List<Offset>>(emptyList()) }
    var currentStrokeColor by remember { mutableStateOf(brushColor) }
    var currentStrokeSize by remember { mutableStateOf(brushSize) }
    val updatedBrushColor by rememberUpdatedState(brushColor)
    val updatedBrushSize by rememberUpdatedState(brushSize)

    // Clear canvas if triggered
    if (clearCanvasSignal) {
        strokes.clear()
        currentPoints = emptyList()
        onCanvasCleared()
    }

    // Undo last stroke if requested
    if (undoLastStrokeSignal) {
        if (strokes.isNotEmpty()) {
            strokes.removeAt(strokes.lastIndex)
        }
        onUndoHandled()
    }

    Box(
        modifier = modifier
            .background(canvasBackgroundColor)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        currentPoints = listOf(offset)
                        currentStrokeColor = updatedBrushColor
                        currentStrokeSize = updatedBrushSize // capture latest size at start
                    },
                    onDragEnd = {
                        if (currentPoints.isNotEmpty()) {
                            strokes.add(
                                StrokePath(
                                    points = currentPoints,
                                    color = currentStrokeColor,
                                    strokeWidth = currentStrokeSize
                                )
                            )
                        }
                        currentPoints = emptyList()
                    },
                    onDrag = { change, _ ->
                        currentPoints = currentPoints + change.position
                    }
                )
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Draw finished strokes
            strokes.forEach { stroke ->
                for (i in 0 until stroke.points.size - 1) {
                    drawLine(
                        color = stroke.color,
                        start = stroke.points[i],
                        end = stroke.points[i + 1],
                        strokeWidth = stroke.strokeWidth.toPx()
                    )
                }
            }

            // Draw in-progress stroke with captured brush values
            for (i in 0 until currentPoints.size - 1) {
                drawLine(
                    color = currentStrokeColor,
                    start = currentPoints[i],
                    end = currentPoints[i + 1],
                    strokeWidth = currentStrokeSize.toPx()
                )
            }
        }
    }
}
