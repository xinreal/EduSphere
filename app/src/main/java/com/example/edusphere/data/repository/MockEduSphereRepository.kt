package com.example.edusphere.data.repository

import com.example.edusphere.data.model.ArContent
import com.example.edusphere.data.model.Course
import com.example.edusphere.data.model.InterestTopic
import com.example.edusphere.data.model.LearningGoal
import com.example.edusphere.data.model.Lesson
import com.example.edusphere.data.model.Module
import com.example.edusphere.data.model.StudyPlan
import com.example.edusphere.data.model.UserLevel
import com.example.edusphere.data.model.UserProfile
import com.example.edusphere.data.model.VirtualClass

class MockEduSphereRepository : EduSphereRepository {

    override fun generateStudyPlan(profile: UserProfile): StudyPlan {
        val primaryInterest = profile.interests.firstOrNull() ?: InterestTopic.ANDROID

        return when {
            profile.goal == LearningGoal.EXAM_PREP || profile.availableMinutesPerDay <= 20 -> {
                StudyPlan(
                    id = "plan_exam_sprint",
                    title = "Focused Sprint",
                    description = "A compact plan for short daily sessions and quick measurable progress.",
                    recommendedDailyMinutes = maxOf(profile.availableMinutesPerDay, 20),
                    whyThisPlan = "Selected because you prefer a shorter daily load and a fast review-focused path.",
                    course = quickRevisionCourse(primaryInterest)
                )
            }

            profile.goal == LearningGoal.CAREER_SWITCH ||
                (profile.level == UserLevel.INTERMEDIATE && profile.availableMinutesPerDay >= 45) -> {
                StudyPlan(
                    id = "plan_career_switch",
                    title = "Career Switch Path",
                    description = "A structured route that combines fundamentals, practice, and portfolio-ready tasks.",
                    recommendedDailyMinutes = maxOf(profile.availableMinutesPerDay, 45),
                    whyThisPlan = "Selected because your goal suggests a practical path with stronger job-oriented outcomes.",
                    course = careerSwitchCourse(primaryInterest)
                )
            }

            profile.goal == LearningGoal.PROJECT_BUILDING || profile.level == UserLevel.ADVANCED -> {
                StudyPlan(
                    id = "plan_project_lab",
                    title = "Project Lab Track",
                    description = "A more applied learning track focused on shipping features and reflective practice.",
                    recommendedDailyMinutes = maxOf(profile.availableMinutesPerDay, 50),
                    whyThisPlan = "Selected because your profile points to project-based learning and higher independence.",
                    course = projectLabCourse(primaryInterest)
                )
            }

            else -> {
                StudyPlan(
                    id = "plan_balanced_growth",
                    title = "Balanced Growth",
                    description = "A steady beginner-friendly plan that mixes theory, practice, and visible milestones.",
                    recommendedDailyMinutes = maxOf(profile.availableMinutesPerDay, 30),
                    whyThisPlan = "Selected because you need a calm path that builds confidence before moving deeper.",
                    course = balancedCourse(primaryInterest)
                )
            }
        }
    }

    override fun getVirtualClasses(): List<VirtualClass> = listOf(
        VirtualClass(
            id = "vc_1",
            title = "Weekly Mentor Session",
            schedule = "Mon · 18:30",
            teacherName = "Elena Smirnova",
            description = "Live Q&A session with roadmap review and mini feedback on the current module.",
            isLive = true
        ),
        VirtualClass(
            id = "vc_2",
            title = "Problem Solving Lab",
            schedule = "Wed · 19:00",
            teacherName = "Maxim Orlov",
            description = "Hands-on class with guided tasks and peer discussion in a virtual classroom format.",
            isLive = false
        ),
        VirtualClass(
            id = "vc_3",
            title = "Demo Day",
            schedule = "Fri · 17:30",
            teacherName = "Anna Volkova",
            description = "Short presentations of student mini-projects and rapid feedback from the instructor.",
            isLive = false
        )
    )

    override fun getArContents(): List<ArContent> = listOf(
        ArContent(
            id = "ar_1",
            title = "3D UI Layout Demo",
            description = "A demo screen for viewing interface blocks in space and understanding layout hierarchy.",
            assetName = "layout_orbit_demo",
            estimatedMinutes = 6
        ),
        ArContent(
            id = "ar_2",
            title = "Interactive Architecture Map",
            description = "A simple AR concept showing data flow between UI, ViewModel, and Repository layers.",
            assetName = "architecture_map_demo",
            estimatedMinutes = 8
        ),
        ArContent(
            id = "ar_3",
            title = "Algorithm Cards in AR",
            description = "Flash-card style 3D objects for quick repetition of important concepts.",
            assetName = "algorithm_cards_demo",
            estimatedMinutes = 5
        )
    )

    override fun findLesson(plan: StudyPlan, lessonId: String): Lesson? {
        return plan.course.modules
            .flatMap { it.lessons }
            .firstOrNull { it.id == lessonId }
    }

    override fun markLessonCompleted(plan: StudyPlan, lessonId: String): StudyPlan {
        val updatedModules = plan.course.modules.map { module ->
            module.copy(
                lessons = module.lessons.map { lesson ->
                    if (lesson.id == lessonId) {
                        lesson.copy(isCompleted = true)
                    } else {
                        lesson
                    }
                }
            )
        }

        return plan.copy(
            course = plan.course.copy(modules = updatedModules)
        )
    }

    private fun balancedCourse(primaryInterest: InterestTopic): Course {
        val title = when (primaryInterest) {
            InterestTopic.ANDROID -> "Android Basics Journey"
            InterestTopic.KOTLIN -> "Kotlin Foundations Journey"
            InterestTopic.UI_UX -> "UI Design for Developers Journey"
            InterestTopic.DATA_STRUCTURES -> "Structured Thinking Journey"
            InterestTopic.AR_VR -> "Interactive Media Journey"
        }

        return Course(
            id = "course_balanced",
            title = title,
            description = "A moderate plan for building a strong base with consistent weekly progress.",
            focusTags = listOf(primaryInterest, InterestTopic.KOTLIN),
            modules = listOf(
                Module(
                    id = "m1",
                    title = "Orientation and Core Concepts",
                    description = "Getting familiar with the subject, navigation, and basic terminology.",
                    lessons = listOf(
                        Lesson("l1", "Welcome to the learning path", "Overview of the learning trajectory and expected outcomes.", 10),
                        Lesson("l2", "Key concepts in context", "A short explanation of the core ideas you will use later.", 14),
                        Lesson("l3", "Mini diagnostic recap", "A gentle recap of what the system learned from your profile.", 8)
                    )
                ),
                Module(
                    id = "m2",
                    title = "Guided Practice",
                    description = "Theory connected with small practical actions.",
                    lessons = listOf(
                        Lesson("l4", "First hands-on task", "A guided exercise with hints and a simple expected result.", 18),
                        Lesson("l5", "Common mistakes", "Typical errors beginners make and how to notice them early.", 12),
                        Lesson("l6", "Checkpoint quiz", "Quick questions to validate understanding.", 10)
                    )
                ),
                Module(
                    id = "m3",
                    title = "Build Confidence",
                    description = "A light project module that turns fragments of knowledge into a small result.",
                    lessons = listOf(
                        Lesson("l7", "Mini project brief", "Define scope and choose the simplest useful version.", 15),
                        Lesson("l8", "Implementation walkthrough", "A sample implementation route with milestones.", 20),
                        Lesson("l9", "Reflection and next step", "Evaluate progress and decide what to deepen next.", 10)
                    )
                )
            )
        )
    }

    private fun quickRevisionCourse(primaryInterest: InterestTopic): Course {
        return Course(
            id = "course_sprint",
            title = when (primaryInterest) {
                InterestTopic.AR_VR -> "AR Quick Review"
                InterestTopic.UI_UX -> "UI Patterns Sprint"
                InterestTopic.DATA_STRUCTURES -> "Core Structures Sprint"
                InterestTopic.KOTLIN -> "Kotlin Fast Review"
                InterestTopic.ANDROID -> "Android Fast Review"
            },
            description = "A short path built around repetition, checkpoints, and frequent feedback signals.",
            focusTags = listOf(primaryInterest),
            modules = listOf(
                Module(
                    id = "s1",
                    title = "Essentials Refresh",
                    description = "Very focused review of the concepts that matter first.",
                    lessons = listOf(
                        Lesson("l10", "High-value concepts", "A compact list of concepts to remember first.", 12),
                        Lesson("l11", "Cheat-sheet session", "Condensed patterns and rules for fast recall.", 9)
                    )
                ),
                Module(
                    id = "s2",
                    title = "Rapid Practice",
                    description = "Quick tasks with immediate correction mindset.",
                    lessons = listOf(
                        Lesson("l12", "Two-minute drills", "A set of tiny exercises to strengthen recall speed.", 10),
                        Lesson("l13", "Mistake repair", "Fix flawed examples and explain why the correction works.", 13),
                        Lesson("l14", "Confidence check", "End-of-module self-check with suggested follow-up.", 8)
                    )
                )
            )
        )
    }

    private fun careerSwitchCourse(primaryInterest: InterestTopic): Course {
        return Course(
            id = "course_career",
            title = "Career Launch Course",
            description = "A practical course for learners who want results that look meaningful in a portfolio.",
            focusTags = listOf(primaryInterest, InterestTopic.UI_UX, InterestTopic.KOTLIN),
            modules = listOf(
                Module(
                    id = "c1",
                    title = "Solid Fundamentals",
                    description = "Build the base you will rely on during real implementation.",
                    lessons = listOf(
                        Lesson("l15", "Fundamentals in practice", "Theory only where it helps make better implementation decisions.", 20),
                        Lesson("l16", "Tooling and workflow", "How to keep your work structured and easier to continue later.", 18),
                        Lesson("l17", "Readable solutions", "Write solutions that are easy to explain and review.", 14)
                    )
                ),
                Module(
                    id = "c2",
                    title = "Portfolio Feature Set",
                    description = "A sequence of features that resembles a simplified real product.",
                    lessons = listOf(
                        Lesson("l18", "Feature planning", "Break down functionality into clean, small chunks.", 16),
                        Lesson("l19", "State and user flow", "Model the interaction flow before polishing details.", 22),
                        Lesson("l20", "Review pass", "Refine the feature and prepare a short explanation of decisions.", 15)
                    )
                ),
                Module(
                    id = "c3",
                    title = "Showcase and Reflection",
                    description = "Prepare what to say about your work and what to improve next.",
                    lessons = listOf(
                        Lesson("l21", "Writing a project summary", "Explain the project clearly without sounding too vague.", 12),
                        Lesson("l22", "Demo checklist", "A short checklist for presenting your result with confidence.", 10)
                    )
                )
            )
        )
    }

    private fun projectLabCourse(primaryInterest: InterestTopic): Course {
        return Course(
            id = "course_project_lab",
            title = "Project Lab",
            description = "An applied path with independent decisions, feature delivery, and iteration.",
            focusTags = listOf(primaryInterest, InterestTopic.AR_VR),
            modules = listOf(
                Module(
                    id = "p1",
                    title = "Problem Framing",
                    description = "Define what you are solving and what can stay out of scope.",
                    lessons = listOf(
                        Lesson("l23", "Outcome first", "Describe the learner benefit before implementation details.", 12),
                        Lesson("l24", "Scope and assumptions", "Reduce the first version to something realistic and demo-friendly.", 15)
                    )
                ),
                Module(
                    id = "p2",
                    title = "Feature Delivery",
                    description = "Build a small but coherent product slice.",
                    lessons = listOf(
                        Lesson("l25", "Core flow implementation", "Implement the most important user journey first.", 24),
                        Lesson("l26", "Adaptive improvement", "Adjust the solution based on usage data or learner profile.", 19),
                        Lesson("l27", "Polish and edge cases", "Tidy the experience and handle the obvious weak points.", 16)
                    )
                ),
                Module(
                    id = "p3",
                    title = "Iteration",
                    description = "Look back, measure progress, and define the next version.",
                    lessons = listOf(
                        Lesson("l28", "What worked and why", "Explain the good parts with evidence from the product flow.", 11),
                        Lesson("l29", "Roadmap for v2", "Choose the next additions without overloading the product.", 10)
                    )
                )
            )
        )
    }
}
