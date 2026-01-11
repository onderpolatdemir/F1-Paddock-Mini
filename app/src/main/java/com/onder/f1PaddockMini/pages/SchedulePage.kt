package com.onder.f1PaddockMini.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onder.f1PaddockMini.features.schedule.domain.model.Race
import com.onder.f1PaddockMini.features.schedule.presentation.ScheduleEvent
import com.onder.f1PaddockMini.features.schedule.presentation.ScheduleViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun SchedulePage(
    scheduleViewModel: ScheduleViewModel = hiltViewModel(),
    onNavigateToRaceResults: (String, String) -> Unit = { _, _ -> },
    onNavigateToQualifyingResults: (String, String) -> Unit = { _, _ -> }
) {
    val state by scheduleViewModel.state.collectAsState()
    
    var yearDropdownExpanded by remember { mutableStateOf(false) }
    var selectedYear by remember { mutableStateOf("2025") }
    
    val years = (2014..2025).map { it.toString() }.reversed()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header with title and year selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Racing",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            // Year selector dropdown
            Box {
                Text(
                    text = selectedYear,
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .clickable { yearDropdownExpanded = true }
                        .padding(8.dp)
                )
                DropdownMenu(
                    expanded = yearDropdownExpanded,
                    onDismissRequest = { yearDropdownExpanded = false },
                    modifier = Modifier.background(Color(0xFF1A1A1A))
                ) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = { 
                                Text(
                                    text = year,
                                    color = Color.White
                                ) 
                            },
                            onClick = {
                                selectedYear = year
                                yearDropdownExpanded = false
                                scheduleViewModel.onEvent(ScheduleEvent.YearChanged(year))
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Race list
        if (state.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading...",
                    color = Color.White
                )
            }
        } else if (state.error.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${state.error}",
                    color = Color.Red
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.races) { race ->
                    RaceRow(
                        race = race,
                        year = state.selectedYear,
                        onRaceClick = {
                            scheduleViewModel.onEvent(ScheduleEvent.RaceExpanded(race.id))
                        },
                        onRaceResultsClick = {
                            onNavigateToRaceResults(state.selectedYear, race.round)
                        },
                        onQualifyingResultsClick = {
                            onNavigateToQualifyingResults(state.selectedYear, race.round)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RaceRow(
    race: Race,
    year: String,
    onRaceClick: () -> Unit,
    onRaceResultsClick: () -> Unit,
    onQualifyingResultsClick: () -> Unit
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onRaceClick)
                .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side: Date and Month
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val dateParts = parseDate(race.date)
                    Text(
                        text = dateParts.first,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .background(
                                Color.White.copy(alpha = 0.2f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = dateParts.second,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }

                // Vertical divider
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(60.dp)
                        .background(Color.Gray.copy(alpha = 0.3f))
                )

                // Right side: Round, Country, Race Name
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Round ${race.round}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = race.country,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    Text(
                        text = race.name,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }

        // Expanded section with buttons
        AnimatedVisibility(
            visible = race.isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F0F0F), RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable(onClick = onRaceResultsClick)
                        .background(Color.Red.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Race Results",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable(onClick = onQualifyingResultsClick)
                        .background(Color.Red.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Qualifying Results",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

fun parseDate(dateString: String): Pair<String, String> {
    return try {
        // Assuming date format is "YYYY-MM-DD" or similar
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        
        val dayFormat = SimpleDateFormat("dd", Locale.getDefault())
        val monthFormat = SimpleDateFormat("MMM", Locale.getDefault())
        
        val day = dayFormat.format(date ?: java.util.Date())
        val month = monthFormat.format(date ?: java.util.Date())
        
        Pair(day, month)
    } catch (e: Exception) {
        // If parsing fails, try to extract manually
        val parts = dateString.split("-")
        if (parts.size >= 3) {
            val day = parts[2]
            val monthNumber = parts[1].toIntOrNull() ?: 1
            val monthNames = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
            val month = if (monthNumber in 1..12) monthNames[monthNumber - 1] else "Jan"
            Pair(day, month)
        } else {
            Pair("01", "Jan")
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun SchedulePagePreview() {
    com.onder.f1PaddockMini.ui.theme.MyApplicationTheme {
        SchedulePage()
    }
}
