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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edusphere.data.model.InterestTopic
import com.example.edusphere.data.model.LearningGoal
import com.example.edusphere.data.model.UserLevel
import com.example.edusphere.data.model.UserProfile

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DiagnosisScreen(
    profile: UserProfile,
    onLevelSelected: (UserLevel) -> Unit,
    onGoalSelected: (LearningGoal) -> Unit,
    onInterestToggle: (InterestTopic) -> Unit,
    onMinutesChanged: (Int) -> Unit,
    onGeneratePlan: () -> Unit,
    onBack: () -> Unit
) {
    AppScaffold(
        title = "Diagnostic",
        canNavigateBack = true,
        onBack = onBack
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tell EduSphere a little about you", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("The prototype uses level, goal, interests, and available time to choose one of several study plans.")
                }
            }

            SectionBlock(title = "Current level") {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    UserLevel.entries.forEach { level ->
                        FilterChip(
                            selected = profile.level == level,
                            onClick = { onLevelSelected(level) },
                            label = { Text(level.toUiText()) }
                        )
                    }
                }
            }

            SectionBlock(title = "Learning goal") {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    LearningGoal.entries.forEach { goal ->
                        FilterChip(
                            selected = profile.goal == goal,
                            onClick = { onGoalSelected(goal) },
                            label = { Text(goal.toUiText()) }
                        )
                    }
                }
            }

            SectionBlock(title = "Topics you like") {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    InterestTopic.entries.forEach { topic ->
                        FilterChip(
                            selected = topic in profile.interests,
                            onClick = { onInterestToggle(topic) },
                            label = { Text(topic.toUiText()) }
                        )
                    }
                }
            }

            SectionBlock(title = "Available time per day") {
                Text("${profile.availableMinutesPerDay} min")
                Slider(
                    value = profile.availableMinutesPerDay.toFloat(),
                    onValueChange = { onMinutesChanged(it.toInt()) },
                    valueRange = 10f..90f,
                    steps = 7
                )
                Text(
                    text = "Lower values produce a shorter plan, while higher values unlock deeper project-oriented tracks.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onGeneratePlan
            ) {
                Text("Generate study plan")
            }
        }
    }
}

@Composable
private fun SectionBlock(
    title: String,
    content: @Composable () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            content()
        }
    }
}

private fun UserLevel.toUiText(): String = when (this) {
    UserLevel.BEGINNER -> "Beginner"
    UserLevel.INTERMEDIATE -> "Intermediate"
    UserLevel.ADVANCED -> "Advanced"
}

private fun LearningGoal.toUiText(): String = when (this) {
    LearningGoal.FOUNDATIONS -> "Foundations"
    LearningGoal.EXAM_PREP -> "Exam prep"
    LearningGoal.CAREER_SWITCH -> "Career switch"
    LearningGoal.PROJECT_BUILDING -> "Project building"
}

private fun InterestTopic.toUiText(): String = when (this) {
    InterestTopic.ANDROID -> "Android"
    InterestTopic.KOTLIN -> "Kotlin"
    InterestTopic.UI_UX -> "UI/UX"
    InterestTopic.DATA_STRUCTURES -> "Data structures"
    InterestTopic.AR_VR -> "AR/VR"
}
