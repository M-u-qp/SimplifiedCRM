package com.example.simplifiedcrm.ui.screens.statistics.component.charts

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

const val TRANSACTION_INTERVAL_MS = 2000L
private var bottomAxisValueFormatter: CartesianValueFormatter = CartesianValueFormatter { _, x, _ ->
    "${x.toInt() + 1}"
}

@Composable
internal fun ColumnChart(
    salesList: List<Long>,
    modifier: Modifier
) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while (isActive) {
                modelProducer.runTransaction {
                    columnSeries { series(salesList) }
                }
                bottomAxisValueFormatter = CartesianValueFormatter { _, x, _ ->
                    "${x.toInt().and(salesList.size)}"
                }
                delay(TRANSACTION_INTERVAL_MS)
            }
        }
    }
    ComposeChart2(modelProducer, modifier)
}

@Composable
private fun ComposeChart2(modelProducer: CartesianChartModelProducer, modifier: Modifier) {
    val chartColor = MaterialTheme.colorScheme.tertiaryContainer
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                ColumnCartesianLayer.ColumnProvider.series(
                    rememberLineComponent(
                        fill = fill(chartColor),
                        thickness = 16.dp,
                        shape = CorneredShape.rounded(allPercent = 40),
                    )
                )
            ),
            startAxis = VerticalAxis.rememberStart(),
            bottomAxis =
            HorizontalAxis.rememberBottom(
                valueFormatter = bottomAxisValueFormatter,
                itemPlacer =
                remember {
                    HorizontalAxis.ItemPlacer.aligned(spacing = 2, addExtremeLabelPadding = true)
                },
            ),
            marker = rememberMarker()
        ),
        modelProducer = modelProducer,
        modifier = modifier,
    )
}

