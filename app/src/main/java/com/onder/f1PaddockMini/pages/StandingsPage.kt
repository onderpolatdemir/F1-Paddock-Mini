package com.onder.f1PaddockMini.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.onder.f1PaddockMini.R
import com.onder.f1PaddockMini.features.drivers.domain.model.Driver
import com.onder.f1PaddockMini.features.drivers.presentation.DriversEvent
import com.onder.f1PaddockMini.features.drivers.presentation.DriversViewModel
import com.onder.f1PaddockMini.features.standings.domain.model.TeamStanding
import com.onder.f1PaddockMini.features.standings.presentation.StandingsEvent
import com.onder.f1PaddockMini.features.standings.presentation.StandingsViewModel

@Composable
fun StandingsPage(
    driversViewModel: DriversViewModel = hiltViewModel(),
    standingsViewModel: StandingsViewModel = hiltViewModel()
) {
    val driversState by driversViewModel.state.collectAsState()
    val standingsState by standingsViewModel.state.collectAsState()
    
    var selectedTab by remember { mutableStateOf("Drivers") }
    var yearDropdownExpanded by remember { mutableStateOf(false) }
    
    // Sync with ViewModels' selected year
    val selectedYear = if (selectedTab == "Drivers") {
        driversState.selectedYear
    } else {
        standingsState.selectedYear
    }

    
    val years = (2014..2025).map { it.toString() }.reversed()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        // Header with title
        Text(
            text = "Standings",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Tabs: Drivers and Constructors with Year selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TabButton(
                    text = "Drivers",
                    isSelected = selectedTab == "Drivers",
                    onClick = { selectedTab = "Drivers" }
                )
                TabButton(
                    text = "Constructors",
                    isSelected = selectedTab == "Constructors",
                    onClick = { selectedTab = "Constructors" }
                )
            }
            
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
                                yearDropdownExpanded = false
                                driversViewModel.onEvent(DriversEvent.YearChanged(year))
                                standingsViewModel.onEvent(StandingsEvent.YearChanged(year))
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected tab
        when (selectedTab) {
            "Drivers" -> {
                if (driversState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading...",
                            color = Color.White
                        )
                    }
                } else if (driversState.error.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${driversState.error}",
                            color = Color.Red
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(driversState.drivers) { driver ->
                            DriverRow(driver = driver)
                        }
                    }
                }
            }
            "Constructors" -> {
                if (standingsState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Loading...",
                            color = Color.White
                        )
                    }
                } else if (standingsState.error.isNotEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${standingsState.error}",
                            color = Color.Red
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(standingsState.standings) { team ->
                            ConstructorRow(team = team)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TabButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isSelected) Color.White else Color.Gray
    val underlineColor = if (isSelected) Color.Red else Color.Transparent

    Column(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = textColor,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(3.dp)
                .background(underlineColor)
                .clip(RoundedCornerShape(bottomEnd = 8.dp))
        )
    }
}

@Composable
fun DriverRow(driver: Driver) {
    val imageRes = driverImageMap[driver.id]
        ?: R.drawable.placeholder_driver // fallback

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Position, Name, Team
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = driver.position.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = driver.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = driver.team,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Right side: Image and Points
            Box {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = driver.name,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "${driver.points.toInt()} PTS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ConstructorRow(team: TeamStanding) {
    val imageRes = driverImageMap[team.teamName]
        ?: R.drawable.placeholder_driver // fallback

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(Color(0xFF1A1A1A), RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left side: Position, Team Name
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = team.position.toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = team.teamName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Right side: Image and Points
            Box {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = team.teamName,
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = "${team.points.toInt()} PTS",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun StandingsPagePreview() {
    com.onder.f1PaddockMini.ui.theme.MyApplicationTheme {
        StandingsPage()
    }
}

