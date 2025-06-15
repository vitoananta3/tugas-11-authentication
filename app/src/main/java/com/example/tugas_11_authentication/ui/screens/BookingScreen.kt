package com.example.tugas_11_authentication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas_11_authentication.data.entity.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingScreen(
    currentUser: User?,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var selectedSpace by remember { mutableStateOf<WorkSpace?>(null) }
    var showBookingDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar with back button
        TopAppBar(
            title = { Text("Book Workspace") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
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
                onSelect = { 
                    selectedSpace = workspace
                    if (currentUser == null) {
                        // Guest user - navigate to login
                        onNavigateToLogin()
                    } else {
                        // Signed in user - show booking dialog
                        showBookingDialog = true
                    }
                }
            )
        }
        }
    }
    
    // Booking dialog
    if (showBookingDialog && selectedSpace != null) {
        AlertDialog(
            onDismissRequest = { 
                showBookingDialog = false
                selectedSpace = null
            },
            title = { Text("Book ${selectedSpace?.name}") },
            text = {
                Text("Booking functionality is not yet implemented. This feature will be available in a future update.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showBookingDialog = false
                        selectedSpace = null
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { 
                        showBookingDialog = false
                        selectedSpace = null
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun WorkSpaceCard(
    workspace: WorkSpace,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        onClick = onSelect,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxHeight()
        ) {
            // Image on the left
            Image(
                painter = painterResource(id = workspace.imageRes),
                contentDescription = workspace.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Content on the right
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top section with title, location, price, and rating
                Column {
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
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Icon(
                                    Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = workspace.location,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(start = 4.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
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
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = workspace.rating.toString(),
                                    fontSize = 13.sp,
                                    modifier = Modifier.padding(start = 2.dp)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Description with fixed height and overflow handling
                    Text(
                        text = workspace.description,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 18.sp
                    )
                }
                
                // Bottom section with capacity and availability
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "${workspace.capacity} people",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                    
                    Surface(
                        color = if (workspace.isAvailable) {
                            MaterialTheme.colorScheme.primaryContainer
                        } else {
                            MaterialTheme.colorScheme.errorContainer
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (workspace.isAvailable) "Available" else "Occupied",
                            fontSize = 11.sp,
                            color = if (workspace.isAvailable) {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            } else {
                                MaterialTheme.colorScheme.onErrorContainer
                            },
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
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
    val isAvailable: Boolean,
    val imageRes: Int
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
            isAvailable = true,
            imageRes = com.example.tugas_11_authentication.R.drawable.room_1
        ),
        WorkSpace(
            id = "2",
            name = "Open Desk #12",
            location = "Floor 2, Main Area",
            description = "Comfortable desk in our vibrant open workspace with high-speed internet and power outlets.",
            pricePerHour = 8,
            capacity = 1,
            rating = 4.5,
            isAvailable = true,
            imageRes = com.example.tugas_11_authentication.R.drawable.room_2
        ),
        WorkSpace(
            id = "3",
            name = "Meeting Room Beta",
            location = "Floor 1, Conference Center",
            description = "Modern meeting room with projector, whiteboard, and video conferencing capabilities.",
            pricePerHour = 35,
            capacity = 8,
            rating = 4.9,
            isAvailable = false,
            imageRes = com.example.tugas_11_authentication.R.drawable.room_3
        ),
        WorkSpace(
            id = "4",
            name = "Creative Studio",
            location = "Floor 4, North Wing",
            description = "Inspiring space with natural light, perfect for creative work and brainstorming sessions.",
            pricePerHour = 15,
            capacity = 6,
            rating = 4.7,
            isAvailable = true,
            imageRes = com.example.tugas_11_authentication.R.drawable.room_4
        ),
        WorkSpace(
            id = "5",
            name = "Phone Booth #3",
            location = "Floor 2, Quiet Zone",
            description = "Private soundproof booth for calls and video meetings.",
            pricePerHour = 12,
            capacity = 1,
            rating = 4.6,
            isAvailable = true,
            imageRes = com.example.tugas_11_authentication.R.drawable.room_5
        )
    )
}