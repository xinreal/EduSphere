package com.example.edusphere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edusphere.data.model.VirtualClass

@Composable
fun VirtualClassScreen(
    classes: List<VirtualClass>,
    onBack: () -> Unit
) {
    AppScaffold(
        title = "Virtual class",
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
            items(classes) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(item.title, style = MaterialTheme.typography.titleLarge)
                        Text("Teacher: ${item.teacherName}")
                        Text("Schedule: ${item.schedule}")
                        Text(item.description)
                        AssistChip(
                            onClick = {},
                            label = { Text(if (item.isLive) "Live now" else "Recorded / scheduled") }
                        )
                    }
                }
            }
        }
    }
}
