package com.example.edusphere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.edusphere.ui.navigation.EduSphereApp
import com.example.edusphere.ui.theme.EduSphereTheme
import com.example.edusphere.ui.viewmodel.EduSphereViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: EduSphereViewModel by viewModels { EduSphereViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EduSphereTheme {
                EduSphereApp(viewModel = viewModel)
            }
        }
    }
}
