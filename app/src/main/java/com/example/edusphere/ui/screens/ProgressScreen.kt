package com.example.edusphere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressScreen(
    progressFraction: Float,
    completedLessons: Int,
    totalLessons: Int,
    onBack: () -> Unit
) {
    AppScaffold(
        title = "Progress",
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
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Overall progress", style = MaterialTheme.typography.headlineSmall)
                    LinearProgressIndicator(progress = { progressFraction }, modifier = Modifier.fillMaxWidth())
                    Text("Completed $completedLessons of $totalLessons lessons")
                    Text("Current status: ${(progressFraction * 100).toInt()}%")
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Adaptive note", style = MaterialTheme.typography.titleMedium)
                    Text(
                        "As lessons are completed, the prototype updates the visible progress. " +
                            "In a full product this state could also rebalance future recommendations."
                    )
                }
            }
        }
    }
}
