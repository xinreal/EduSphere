package com.example.edusphere.data.model

enum class UserLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}

enum class LearningGoal {
    FOUNDATIONS,
    EXAM_PREP,
    CAREER_SWITCH,
    PROJECT_BUILDING
}

enum class InterestTopic {
    ANDROID,
    KOTLIN,
    UI_UX,
    DATA_STRUCTURES,
    AR_VR
}

data class UserProfile(
    val level: UserLevel = UserLevel.BEGINNER,
    val goal: LearningGoal = LearningGoal.FOUNDATIONS,
    val interests: List<InterestTopic> = listOf(InterestTopic.ANDROID),
    val availableMinutesPerDay: Int = 30
)

data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val isCompleted: Boolean = false
)

data class Module(
    val id: String,
    val title: String,
    val description: String,
    val lessons: List<Lesson>
)

data class Course(
    val id: String,
    val title: String,
    val description: String,
    val focusTags: List<InterestTopic>,
    val modules: List<Module>
)

data class StudyPlan(
    val id: String,
    val title: String,
    val description: String,
    val recommendedDailyMinutes: Int,
    val whyThisPlan: String,
    val course: Course
)

data class VirtualClass(
    val id: String,
    val title: String,
    val schedule: String,
    val teacherName: String,
    val description: String,
    val isLive: Boolean
)

data class ArContent(
    val id: String,
    val title: String,
    val description: String,
    val assetName: String,
    val estimatedMinutes: Int
)
