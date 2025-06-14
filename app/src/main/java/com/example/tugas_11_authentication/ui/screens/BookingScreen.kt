package com.example.tugas_11_authentication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookingScreen(
    onNavigateBack: () -> Unit
) {
    var selectedSpace by remember { mutableStateOf<WorkSpace?>(null) }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Book Workspace",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
        
        item {
            Text(
                text = "Available Spaces",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(getSampleWorkSpaces()) { workspace ->
            WorkSpaceCard(
                workspace = workspace,
                isSelected = selectedSpace == workspace,
                onSelect = { selectedSpace = workspace }
            )
        }

        if (selectedSpace != null) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        // Handle booking logic here
                        // For demo, just show a simple message
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Book ${selectedSpace?.name}")
                }
            }
        }
    }
}

@Composable
fun WorkSpaceCard(
    workspace: WorkSpace,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onSelect,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = workspace.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = workspace.location,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "$${workspace.pricePerHour}/hr",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = workspace.rating.toString(),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = workspace.description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Capacity: ${workspace.capacity} people",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Text(
                    text = if (workspace.isAvailable) "Available" else "Occupied",
                    fontSize = 12.sp,
                    color = if (workspace.isAvailable) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

data class WorkSpace(
    val id: String,
    val name: String,
    val location: String,
    val description: String,
    val pricePerHour: Int,
    val capacity: Int,
    val rating: Double,
    val isAvailable: Boolean
)

fun getSampleWorkSpaces(): List<WorkSpace> {
    return listOf(
        WorkSpace(
            id = "1",
            name = "Executive Suite A",
            location = "Floor 3, East Wing",
            description = "Premium private office with city view, perfect for important meetings and focused work.",
            pricePerHour = 25,
            capacity = 4,
            rating = 4.8,
            isAvailable = true
        ),
        WorkSpace(
            id = "2",
            name = "Open Desk #12",
            location = "Floor 2, Main Area",
            description = "Comfortable desk in our vibrant open workspace with high-speed internet and power outlets.",
            pricePerHour = 8,
            capacity = 1,
            rating = 4.5,
            isAvailable = true
        ),
        WorkSpace(
            id = "3",
            name = "Meeting Room Beta",
            location = "Floor 1, Conference Center",
            description = "Modern meeting room with projector, whiteboard, and video conferencing capabilities.",
            pricePerHour = 35,
            capacity = 8,
            rating = 4.9,
            isAvailable = false
        ),
        WorkSpace(
            id = "4",
            name = "Creative Studio",
            location = "Floor 4, North Wing",
            description = "Inspiring space with natural light, perfect for creative work and brainstorming sessions.",
            pricePerHour = 15,
            capacity = 6,
            rating = 4.7,
            isAvailable = true
        ),
        WorkSpace(
            id = "5",
            name = "Phone Booth #3",
            location = "Floor 2, Quiet Zone",
            description = "Private soundproof booth for calls and video meetings.",
            pricePerHour = 12,
            capacity = 1,
            rating = 4.6,
            isAvailable = true
        )
    )
}