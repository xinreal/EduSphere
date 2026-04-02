package com.example.edusphere.ui.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.edusphere.ui.screens.ArMaterialsScreen
import com.example.edusphere.ui.screens.DiagnosisScreen
import com.example.edusphere.ui.screens.LessonScreen
import com.example.edusphere.ui.screens.ModulesScreen
import com.example.edusphere.ui.screens.PlanScreen
import com.example.edusphere.ui.screens.ProgressScreen
import com.example.edusphere.ui.screens.VirtualClassScreen
import com.example.edusphere.ui.screens.WelcomeScreen
import com.example.edusphere.ui.viewmodel.EduSphereViewModel

@Composable
fun EduSphereApp(
    viewModel: EduSphereViewModel
) {
    val navController = rememberNavController()

    Surface(color = MaterialTheme.colorScheme.background) {
        NavHost(
            navController = navController,
            startDestination = Screen.Welcome.route
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(
                    onStartClick = { navController.navigate(Screen.Diagnosis.route) }
                )
            }

            composable(Screen.Diagnosis.route) {
                DiagnosisScreen(
                    profile = viewModel.userProfile,
                    onLevelSelected = viewModel::updateLevel,
                    onGoalSelected = viewModel::updateGoal,
                    onInterestToggle = viewModel::toggleInterest,
                    onMinutesChanged = viewModel::updateAvailableMinutes,
                    onGeneratePlan = {
                        viewModel.generatePlan()
                        navController.navigate(Screen.StudyPlan.route)
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.StudyPlan.route) {
                val plan = viewModel.studyPlan
                if (plan == null) {
                    Text("No study plan yet", modifier = Modifier)
                } else {
                    PlanScreen(
                        studyPlan = plan,
                        progressFraction = viewModel.progressFraction,
                        onModulesClick = { navController.navigate(Screen.Modules.route) },
                        onProgressClick = { navController.navigate(Screen.Progress.route) },
                        onVirtualClassClick = { navController.navigate(Screen.VirtualClass.route) },
                        onArClick = { navController.navigate(Screen.ArMaterials.route) },
                        onBack = { navController.popBackStack() }
                    )
                }
            }

            composable(Screen.Modules.route) {
                val modules = viewModel.studyPlan?.course?.modules.orEmpty()
                ModulesScreen(
                    modules = modules,
                    onLessonClick = { lessonId ->
                        navController.navigate(Screen.Lesson.createRoute(lessonId))
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            composable(
                route = Screen.Lesson.route,
                arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
            ) { backStackEntry ->
                val lessonId = backStackEntry.arguments?.getString("lessonId").orEmpty()
                val lesson = viewModel.findLesson(lessonId)

                if (lesson == null) {
                    Text("Lesson not found")
                } else {
                    LessonScreen(
                        lesson = lesson,
                        onMarkCompleted = { viewModel.markLessonCompleted(lessonId) },
                        onOpenProgress = { navController.navigate(Screen.Progress.route) },
                        onBack = { navController.popBackStack() }
                    )
                }
            }

            composable(Screen.Progress.route) {
                ProgressScreen(
                    progressFraction = viewModel.progressFraction,
                    completedLessons = viewModel.completedLessons,
                    totalLessons = viewModel.totalLessons,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.VirtualClass.route) {
                VirtualClassScreen(
                    classes = viewModel.virtualClasses,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Screen.ArMaterials.route) {
                ArMaterialsScreen(
                    arContents = viewModel.arContents,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
