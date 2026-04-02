package com.example.edusphere.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edusphere.data.model.Module

@Composable
fun ModulesScreen(
    modules: List<Module>,
    onLessonClick: (String) -> Unit,
    onBack: () -> Unit
) {
    AppScaffold(
        title = "Modules and lessons",
        canNavigateBack = true,
        onBack = onBack
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(modules) { module ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(module.title, style = MaterialTheme.typography.titleLarge)
                        Text(module.description)
                        HorizontalDivider()
                        module.lessons.forEach { lesson ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onLessonClick(lesson.id) }
                                    .padding(vertical = 6.dp)
                            ) {
                                Text(lesson.title, style = MaterialTheme.typography.titleMedium)
                                Text("${lesson.durationMinutes} min · ${lesson.description}")
                                if (lesson.isCompleted) {
                                    AssistChip(
                                        onClick = {},
                                        label = { Text("Completed") }
                                    )
                                }
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
