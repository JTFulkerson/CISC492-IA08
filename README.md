# IA08 Doodle App

Simple Jetpack Compose doodle application built for CISC 482. The interface is fully declarative: a tool bar controls brush size, color, undo, and clearing the canvas, while the drawing surface stores every stroke in Compose state and renders smooth, rounded paths as you draw.

## Features

- Adjustable brush sizes and color palette from the bottom tool panel.
- Undo button to remove the most recent stroke without affecting the rest of the canvas.
- Clear button to reset the drawing surface instantly.

## Prerequisites

- Android Studio Iguana (or newer) with Android SDK 24+ installed.
- An Android device or emulator running API 24 or newer.
- Optional: Java 11+ and the Android command-line tools if you plan to build via Gradle only.

## How to Run

### Android Studio
1. Clone the repository: `git clone https://github.com/JTFulkerson/CISC492-IA08.git`
2. Open the project in Android Studio.
3. Let Gradle sync; install any prompted SDK components.
4. Choose a device (emulator or physical) and press **Run**. The doodle canvas loads immediately; use the toolbar to pick colors, brush sizes, undo, or clear.

### Command Line
1. Ensure the Android SDK path is set in `local.properties` (Android Studio usually generates this automatically).
2. From the project root run: `./gradlew installDebug`
3. Launch the app from your connected device or emulator.

## References

- [Jetpack Compose Canvas and DrawScope documentation](https://developer.android.com/jetpack/compose/graphics/draw) – guidance for `Canvas`, `drawPath`, and state-driven drawing.
- [Pointer input gestures in Compose](https://developer.android.com/reference/kotlin/androidx/compose/foundation/gestures/package-summary#detectdraggestures) - handling drag gestures via `detectDragGestures`.
- [Compose Path and Bézier helpers](https://developer.android.com/reference/kotlin/androidx/compose/ui/graphics/Path) – used for smoothing sampled points into rounded brush strokes.

## AI Assistance Disclaimer

GPT-5.1-Codex (Preview) was used to assist with several comments/doccumentation and helper functions—specifically the brush stroke smoothing/offset logic and related state explanations. All generated suggestions were reviewed, tested, and integrated manually before submission.
