package hr.foi.air.concertstager.ui.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.concertstager.ui.theme.Purple40
import hr.foi.air.concertstager.ui.theme.Purple


@Composable
fun ReviewGraph(
    reviews: List<Int>,
    modifier: Modifier = Modifier
) {
    val maxRating = 5
    val ratingCounts = IntArray(maxRating + 1)

    // Count the number of occurrences for each rating
    reviews.forEach { rating ->
        if (rating in 0..maxRating) {
            ratingCounts[rating]++
        }
    }
    Box(
        modifier = modifier
            .width(220.dp)
            .height(230.dp)
    ){

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("", style = MaterialTheme.typography.bodyMedium)

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(160.dp) // Set a fixed height for the row
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxHeight()
                        .padding(top = 19.dp, start = 45.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    for (i in 1..maxRating) {
                        Text(
                            text = i.toString(),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                        )
                    }
                }

                // Divider
                Divider(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(3.dp) // Set the width of the divider
                )

                // Canvas with graph
                Canvas(
                    modifier = Modifier
                        .height(120.dp)
                        .weight(1f)
                        .rotate(90f)// Optional padding for the graph
                ) {
                    drawIntoCanvas { canvas ->
                        val maxCount = ratingCounts.maxOrNull()?.toFloat() ?: 1f
                        val barCount = ratingCounts.size
                        val barWidth = size.width / (barCount * 4) // Add padding between bars

                        ratingCounts.forEachIndexed { rating, count ->
                            val barHeight = (count.toFloat() / maxCount) * size.height
                            val startX = rating * (barWidth * 5) // Multiply by 2 to account for padding
                            val paint = android.graphics.Paint().apply {
                                color = Purple40.toArgb()
                            }

                            canvas.nativeCanvas.drawRect(
                                startX, size.height - barHeight, startX + barWidth, size.height,
                                paint
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowGraph(
    modifier: Modifier = Modifier
) {
    val reviews = listOf( 1,3,2,3,2,5,5,5,5,5,5,5,5,5,5,5,5,5,4,4,4,3,3,3,3,3,3,4,4,4,4)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = modifier
                .width(220.dp)
                .height(230.dp)
        ){
            ReviewGraph(reviews = reviews)

        }
    }
}

