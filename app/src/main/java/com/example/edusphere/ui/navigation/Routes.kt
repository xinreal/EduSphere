package com.example.edusphere.ui.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome")
    data object Diagnosis : Screen("diagnosis")
    data object StudyPlan : Screen("study_plan")
    data object Modules : Screen("modules")
    data object Progress : Screen("progress")
    data object VirtualClass : Screen("virtual_class")
    data object ArMaterials : Screen("ar_materials")
    data object Lesson : Screen("lesson/{lessonId}") {
        fun createRoute(lessonId: String): String = "lesson/$lessonId"
    }
}
