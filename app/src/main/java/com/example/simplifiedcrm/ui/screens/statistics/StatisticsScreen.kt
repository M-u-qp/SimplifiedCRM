package com.example.simplifiedcrm.ui.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar
import com.example.simplifiedcrm.ui.screens.statistics.component.charts.ColumnChart
import com.example.simplifiedcrm.ui.screens.statistics.component.charts.LineChart

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val state = viewModel.state.collectAsState().value

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        ambientColor = MaterialTheme.colorScheme.onSurface,
                        spotColor = MaterialTheme.colorScheme.onSurface
                    )
            ) {
                TaskTopBar(
                    title = stringResource(id = R.string.statistics),
                    navigationIcon = R.drawable.icons8_arrow,
                    clickNavigationIcon = navigateUp,
                    navigationIconModifier = Modifier.graphicsLayer(rotationZ = 180f),
                    sortOrderList = listOf()
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            StatisticsContent(
                salesList = state.salesList,
                totalSales = state.totalSales,
                onNextMonth = { viewModel.goToNextMonth() },
                onPreviousMonth = { viewModel.goToPreviousMonth() },
                currentSelectDate = state.currentSelectRangeDate
            )
        }
    }
}

@Composable
private fun StatisticsContent(
    salesList: List<Long>,
    totalSales: Long,
    onNextMonth: () -> Unit,
    onPreviousMonth: () -> Unit,
    currentSelectDate: String
) {
    var isChangedChart by remember { mutableStateOf(false) }
    var isVisibleDialogPercentage by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.medium
            ),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    modifier = Modifier.align(Alignment.TopStart),
                    onClick = { isVisibleDialogPercentage = !isVisibleDialogPercentage }
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.icons8_percent),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }

                IconButton(
                    modifier = Modifier.align(Alignment.TopEnd),
                    onClick = { isChangedChart = !isChangedChart }
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.icons8_statistic),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                if (!isChangedChart) {
                    LineChart(
                        salesList = salesList,
                        modifier = Modifier
                    )
                } else {
                    ColumnChart(
                        salesList = salesList,
                        modifier = Modifier
                    )
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.BottomEnd),
                    text = stringResource(id = R.string.total_sales) +
                            ": " + totalSales.toString() +
                            " " +
                            stringResource(id = R.string.rub),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onPreviousMonth) {
                    Icon(
                        modifier = Modifier.graphicsLayer(rotationZ = 180f),
                        bitmap = ImageBitmap.imageResource(R.drawable.icons8_next),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
                Text(
                    text = currentSelectDate,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.surface
                )
                IconButton(onClick = onNextMonth) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(R.drawable.icons8_next),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }
    }
}