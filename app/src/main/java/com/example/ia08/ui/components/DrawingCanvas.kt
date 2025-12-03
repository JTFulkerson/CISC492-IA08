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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
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
                if (stroke.points.isNotEmpty()) {
                    drawPath(
                        path = buildSmoothPath(stroke.points),
                        color = stroke.color,
                        style = Stroke(
                            width = stroke.strokeWidth.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // Draw in-progress stroke with captured brush values
            if (currentPoints.size >= 2) {
                drawPath(
                    path = buildSmoothPath(currentPoints),
                    color = currentStrokeColor,
                    style = Stroke(
                        width = currentStrokeSize.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

private fun buildSmoothPath(points: List<Offset>): Path {
    val path = Path()
    if (points.isEmpty()) return path

    path.moveTo(points.first().x, points.first().y)
    if (points.size == 1) {
        path.lineTo(points.first().x, points.first().y)
        return path
    }

    for (i in 1 until points.size) {
        val previous = points[i - 1]
        val current = points[i]
        val midPoint = Offset(
            (previous.x + current.x) / 2f,
            (previous.y + current.y) / 2f
        )
        path.quadraticTo(previous.x, previous.y, midPoint.x, midPoint.y)
    }

    path.lineTo(points.last().x, points.last().y)
    return path
}
