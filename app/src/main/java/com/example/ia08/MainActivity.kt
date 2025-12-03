package com.example.ia08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ia08.ui.components.DrawingCanvas
import com.example.ia08.ui.components.ToolPanel
import com.example.ia08.ui.theme.IA08Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            IA08Theme {
                var brushColor by remember { mutableStateOf(Color.Black) }
                var brushSize by remember { mutableStateOf(8f) }
                var clearCanvas by remember { mutableStateOf(false) }
                var undoLastStroke by remember { mutableStateOf(false) }
                val canvasBackground = MaterialTheme.colorScheme.background

                Scaffold(
                    bottomBar = {
                        ToolPanel(
                            brushSize = brushSize,
                            currentColor = brushColor,
                            onBrushSizeChange = { brushSize = it },
                            onColorChange = { brushColor = it },
                            onClearCanvas = {
                                clearCanvas = true
                                undoLastStroke = false
                            },
                            onUndoLastStroke = { undoLastStroke = true }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        DrawingCanvas(
                            brushColor = brushColor,
                            brushSize = brushSize.dp,
                            clearCanvasSignal = clearCanvas,
                            onCanvasCleared = { clearCanvas = false },
                            undoLastStrokeSignal = undoLastStroke,
                            onUndoHandled = { undoLastStroke = false },
                            canvasBackgroundColor = canvasBackground,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
