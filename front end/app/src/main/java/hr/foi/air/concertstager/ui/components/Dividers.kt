package hr.foi.air.concertstager.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LineDivider(
    modifier: Modifier = Modifier,
    text: String
){
    Row(
        modifier = modifier
            .padding(start = 20.dp, end= 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(modifier = Modifier.weight(1f))
        Text(text = text, modifier = Modifier.padding(horizontal = 8.dp))
        Divider(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun ShowDivider(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LineDivider(text = "OR")
    }
}
