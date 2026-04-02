package com.example.edusphere.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.edusphere.data.model.ArContent
import com.example.edusphere.data.model.InterestTopic
import com.example.edusphere.data.model.LearningGoal
import com.example.edusphere.data.model.Lesson
import com.example.edusphere.data.model.StudyPlan
import com.example.edusphere.data.model.UserLevel
import com.example.edusphere.data.model.UserProfile
import com.example.edusphere.data.model.VirtualClass
import com.example.edusphere.data.repository.EduSphereRepository
import com.example.edusphere.data.repository.MockEduSphereRepository

class EduSphereViewModel(
    private val repository: EduSphereRepository
) : ViewModel() {

    var userProfile by mutableStateOf(UserProfile())
        private set

    var studyPlan by mutableStateOf<StudyPlan?>(null)
        private set

    val virtualClasses: List<VirtualClass> = repository.getVirtualClasses()
    val arContents: List<ArContent> = repository.getArContents()

    fun updateLevel(level: UserLevel) {
        userProfile = userProfile.copy(level = level)
    }

    fun updateGoal(goal: LearningGoal) {
        userProfile = userProfile.copy(goal = goal)
    }

    fun toggleInterest(topic: InterestTopic) {
        val updated = if (topic in userProfile.interests) {
            userProfile.interests.filterNot { it == topic }
        } else {
            userProfile.interests + topic
        }

        userProfile = userProfile.copy(
            interests = if (updated.isEmpty()) listOf(InterestTopic.ANDROID) else updated
        )
    }

    fun updateAvailableMinutes(minutes: Int) {
        userProfile = userProfile.copy(availableMinutesPerDay = minutes)
    }

    fun generatePlan() {
        studyPlan = repository.generateStudyPlan(userProfile)
    }

    fun markLessonCompleted(lessonId: String) {
        val currentPlan = studyPlan ?: return
        studyPlan = repository.markLessonCompleted(currentPlan, lessonId)
    }

    fun findLesson(lessonId: String): Lesson? {
        val currentPlan = studyPlan ?: return null
        return repository.findLesson(currentPlan, lessonId)
    }

    val totalLessons: Int
        get() = studyPlan?.course?.modules?.sumOf { it.lessons.size } ?: 0

    val completedLessons: Int
        get() = studyPlan?.course?.modules?.sumOf { module ->
            module.lessons.count { it.isCompleted }
        } ?: 0

    val progressFraction: Float
        get() = if (totalLessons == 0) 0f else completedLessons.toFloat() / totalLessons.toFloat()

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                return EduSphereViewModel(MockEduSphereRepository()) as T
            }
        }
    }
}
