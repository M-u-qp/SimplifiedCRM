package com.example.simplifiedcrm.ui.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simplifiedcrm.R
import com.example.simplifiedcrm.ui.screens.component.TaskTopBar
import com.example.simplifiedcrm.ui.screens.statistics.component.Chart1

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
                totalSales = state.totalSales
            )
        }
    }
}

@Composable
private fun StatisticsContent(
    salesList: List<Long>,
    totalSales: Long
) {
    LazyColumn(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
//        item {
//            Text(
//                text = totalSales.toString(),
//                style = MaterialTheme.typography.titleSmall
//            )
//        }
//        items(salesList.size) {
//            Text(
//                text = salesList[it].toString(),
//                style = MaterialTheme.typography.titleSmall
//            )
//        }
        item {
            Chart1(
                salesList = salesList,
                modifier = Modifier
            )
        }
    }
}