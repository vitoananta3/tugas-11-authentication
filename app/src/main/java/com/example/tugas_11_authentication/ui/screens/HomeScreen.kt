package com.example.tugas_11_authentication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Person
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
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    user: User?,
    onNavigateToBook: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Welcome Header
            Column {
                Text(
                    text = "Welcome!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = user?.fullName ?: "User",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // User Info Section
        item {
            if (user != null) {
                UserInfoCard(user = user, dateFormatter = dateFormatter)
            }
        }

        // Book Button Section
        item {
            BookingCard(onNavigateToBook = onNavigateToBook)
        }

        // News Section
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Latest News",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(
                    onClick = { /* TODO: Navigate to all news */ }
                ) {
                    Text(
                        text = "See All",
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Sample news items
        items(getSampleNews()) { news ->
            NewsCard(news = news)
        }
    }
}

@Composable
fun UserInfoCard(
    user: User,
    dateFormatter: SimpleDateFormat
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Welcome!",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = user.fullName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Member since ${dateFormatter.format(user.createdAt)}",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
fun BookingCard(
    onNavigateToBook: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Ready to work?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Book your perfect workspace now",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onNavigateToBook,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Book Now")
            }
        }
    }
}

@Composable
fun NewsCard(
    news: NewsItem
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            // Image on the left
            Image(
                painter = painterResource(id = news.imageRes),
                contentDescription = news.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Text content on the right
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = news.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = news.summary,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = news.date,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

data class NewsItem(
    val title: String,
    val summary: String,
    val date: String,
    val imageRes: Int
)

fun getSampleNews(): List<NewsItem> {
    return listOf(
        NewsItem(
            title = "New Meeting Rooms Available",
            summary = "We've added 5 new state-of-the-art meeting rooms with the latest technology.",
            date = "Dec 15, 2024",
            imageRes = com.example.tugas_11_authentication.R.drawable.news_1
        ),
        NewsItem(
            title = "Holiday Hours Update",
            summary = "Check out our updated holiday schedule for the upcoming season.",
            date = "Dec 10, 2024",
            imageRes = com.example.tugas_11_authentication.R.drawable.news_2
        ),
        NewsItem(
            title = "Networking Event This Friday",
            summary = "Join us for our monthly networking event with local entrepreneurs.",
            date = "Dec 8, 2024",
            imageRes = com.example.tugas_11_authentication.R.drawable.news_3
        ),
        NewsItem(
            title = "New Coffee Bar Menu",
            summary = "Try our new selection of artisanal coffees and healthy snacks.",
            date = "Dec 5, 2024",
            imageRes = com.example.tugas_11_authentication.R.drawable.news_1
        )
    )
}