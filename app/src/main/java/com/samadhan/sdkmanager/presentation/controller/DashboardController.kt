package com.samadhan.sdkmanager.presentation.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.samadhan.sdkmanager.domain.viewModel.DashboardViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DashboardController(
    navController: NavHostController,
    dashboardViewModel: DashboardViewModel = hiltViewModel()
) {
    val state = dashboardViewModel.uiState
    Surface {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            )
            {
                // Header with Title and Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Active Polls",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { /* Scan Poll Action */ }) {
                        Text("Scan Poll")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Search Box
                OutlinedTextField(
                    value = "",
                    onValueChange = { },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Table Header
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3F4F6))
                        .padding(vertical = 8.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Poll", fontWeight = FontWeight.SemiBold)
                    Text("Actions", fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Poll List
                Column(
                    modifier = Modifier
                ) {
                    dashboardViewModel.uiState.value.response?.content?.forEach { poll ->
                        PollRow(pollName = poll.title, date = poll.pollDate, poll.id)
                        Spacer(modifier = Modifier.height(6.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Pagination
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = {}, enabled = false) {
                        Text("< Previous")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("1", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {}, enabled = false) {
                        Text("Next >")
                    }
                }
            }
        }
    }
}


@Composable
fun PollRow(pollName: String, date: String, id: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = pollName, fontWeight = FontWeight.Medium)
            Text(
                text = formatDate(date),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(
                onClick = { /* Info */ },
                modifier = Modifier.background(Color(0xFFFFC107), CircleShape)
            ) {
                Icon(Icons.Default.Info, contentDescription = "Info", tint = Color.White)
            }
            IconButton(
                onClick = { /* QR */ },
                modifier = Modifier.background(Color(0xFF2196F3), CircleShape)
            ) {
                Icon(Icons.Default.Settings, contentDescription = "QR", tint = Color.White)
            }
            IconButton(
                onClick = { /* Done */ },
                modifier = Modifier.background(Color(0xFF4CAF50), CircleShape)
            ) {
                Icon(Icons.Default.Check, contentDescription = "Done", tint = Color.White)
            }
        }
    }
}


fun formatDate(inputDate: String): String {
    // Parse the original date string
    val parsedDate = LocalDate.parse(inputDate, DateTimeFormatter.ISO_DATE)

    // Format to desired pattern
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    return parsedDate.format(formatter)
}