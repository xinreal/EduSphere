package com.example.edusphere.data.repository

import com.example.edusphere.data.model.ArContent
import com.example.edusphere.data.model.Lesson
import com.example.edusphere.data.model.StudyPlan
import com.example.edusphere.data.model.UserProfile
import com.example.edusphere.data.model.VirtualClass

interface EduSphereRepository {
    fun generateStudyPlan(profile: UserProfile): StudyPlan
    fun getVirtualClasses(): List<VirtualClass>
    fun getArContents(): List<ArContent>
    fun findLesson(plan: StudyPlan, lessonId: String): Lesson?
    fun markLessonCompleted(plan: StudyPlan, lessonId: String): StudyPlan
}
