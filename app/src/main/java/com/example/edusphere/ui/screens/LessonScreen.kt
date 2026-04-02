package com.example.edusphere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edusphere.data.model.Lesson

@Composable
fun LessonScreen(
    lesson: Lesson,
    onMarkCompleted: () -> Unit,
    onOpenProgress: () -> Unit,
    onBack: () -> Unit
) {
    AppScaffold(
        title = "Lesson",
        canNavigateBack = true,
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(lesson.title, style = MaterialTheme.typography.headlineSmall)
                    Text("Estimated time: ${lesson.durationMinutes} min")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(lesson.description)
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onMarkCompleted,
                enabled = !lesson.isCompleted
            ) {
                Text(if (lesson.isCompleted) "Already completed" else "Mark as completed")
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onOpenProgress
            ) {
                Text("See updated progress")
            }
        }
    }
}
