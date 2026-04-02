package com.example.edusphere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edusphere.data.model.StudyPlan

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlanScreen(
    studyPlan: StudyPlan,
    progressFraction: Float,
    onModulesClick: () -> Unit,
    onProgressClick: () -> Unit,
    onVirtualClassClick: () -> Unit,
    onArClick: () -> Unit,
    onBack: () -> Unit
) {
    AppScaffold(
        title = "Your study plan",
        canNavigateBack = true,
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(studyPlan.title, style = MaterialTheme.typography.headlineSmall)
                    Text(studyPlan.description)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Course: ${studyPlan.course.title}", style = MaterialTheme.typography.titleMedium)
                    Text("Daily load: ${studyPlan.recommendedDailyMinutes} min")
                    Text("Why this plan: ${studyPlan.whyThisPlan}")
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text("Progress snapshot", style = MaterialTheme.typography.titleMedium)
                    LinearProgressIndicator(progress = { progressFraction }, modifier = Modifier.fillMaxWidth())
                    Text("${(progressFraction * 100).toInt()}% completed")
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Focus tags", style = MaterialTheme.typography.titleMedium)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        studyPlan.course.focusTags.forEach { tag ->
                            FilterChip(
                                selected = true,
                                onClick = {},
                                label = { Text(tag.name.replace('_', '/')) }
                            )
                        }
                    }
                }
            }

            Button(modifier = Modifier.fillMaxWidth(), onClick = onModulesClick) {
                Text("Open modules and lessons")
            }
            OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onProgressClick) {
                Text("View progress")
            }
            OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onVirtualClassClick) {
                Text("Open virtual class")
            }
            OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onArClick) {
                Text("Open AR materials demo")
            }
        }
    }
}
