package com.example.edusphere.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.edusphere.data.model.ArContent

@Composable
fun ArMaterialsScreen(
    arContents: List<ArContent>,
    onBack: () -> Unit
) {
    AppScaffold(
        title = "AR materials",
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
            item {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Demo screen", style = MaterialTheme.typography.titleLarge)
                        Text("This prototype does not include real AR rendering. It demonstrates the section structure and content cards for future integration.")
                    }
                }
            }

            items(arContents) { item ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(item.title, style = MaterialTheme.typography.titleMedium)
                        Text(item.description)
                        Text("Asset key: ${item.assetName}")
                        Text("Estimated session: ${item.estimatedMinutes} min")
                    }
                }
            }
        }
    }
}
