package hr.foi.air.concertstager.ui.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.foi.air.concertstager.ui.theme.BlueLight
import hr.foi.air.concertstager.ui.theme.Purple
import hr.foi.air.concertstager.viewmodels.Concert.CreateConcertViewModel
import java.util.Calendar
import java.util.Date
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.core.login.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcertForm1(
    onSaveClicked: () -> Unit,
    viewModel: CreateConcertViewModel = viewModel()
) {

    LaunchedEffect(true) {
        viewModel.getVenues()
    }

    val name = viewModel.name.observeAsState().value?:""
    var beginDate by remember { mutableStateOf<Date?>(Date()) }
    var endDate by remember { mutableStateOf<Date?>(Date())}
    val description = viewModel.description.observeAsState().value?:""
    val selectedVenue = viewModel.selectedVenue.observeAsState()
    val venues by viewModel.venues.observeAsState()
    val errorMessage = viewModel.errorMessage.observeAsState().value?:""
    Log.i("BEGINDATE", beginDate.toString())

    val context = LocalContext.current
    if(venues != null && selectedVenue.value != null){
        Box(
            modifier = Modifier
                .width(402.dp)
                .height(750.dp)
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(Purple)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    if(errorMessage.isNotEmpty()){
                        Row(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = errorMessage, color = Color.Red)
                        }
                    }
                    // TextField for Name
                    TextField(
                        value = name,
                        onValueChange = { viewModel.name.value = it },
                        label = { Text("Name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = BlueLight
                        )
                    )
                    // TextField for Date with DatePicker
                    TextField(
                        value = DateFormatter.getDate(beginDate.toString()),
                        onValueChange = {},
                        label = { Text("Date") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = BlueLight
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                // Show DatePicker when clicking on the trailing icon
                                showDateTimePicker(context, Date()) { selectedDate ->
                                    beginDate = selectedDate
                                    viewModel.startDate.value = DateFormatter.getDate(selectedDate.toString())
                                    Log.i("SELEKTIRANSTART", viewModel.startDate.value.toString())
                                }
                            }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                            }
                        }
                    )

                    TextField(
                        value = DateFormatter.getDate(endDate.toString()),
                        onValueChange = {},
                        label = { Text("EndDate") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = BlueLight
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                // Show DatePicker when clicking on the trailing icon
                                showDateTimePicker(context, Date()) { selectedDate ->
                                    endDate = selectedDate
                                    viewModel.endDate.value = DateFormatter.getDate(selectedDate.toString())
                                    Log.i("SELEKTIRANEND", viewModel.endDate.value.toString())
                                }
                            }) {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
                            }
                        }
                    )
                        VenueStyledComboBox(
                            label = "",
                            items = venues!!,
                            onItemSelected = {viewModel.selectedVenue.value = it},
                            selectedItem = selectedVenue.value!!)

                    // TextField for Number of Performers

                    TextField(
                        value = description,
                        onValueChange = { viewModel.description.value = it },
                        label = { Text("Description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(bottom = 16.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = BlueLight
                        )
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        SmallButtonWithIcon(
                            label = "Save",
                            imageVector = Icons.Default.Create,
                            onClick = {
                                viewModel.createConcert(onSaveClicked)
                            }
                        )
                    }
                }
            }
        }
    }else{
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            contentAlignment = Alignment.Center){
            CircularProgressIndicator(modifier = Modifier.size(50.dp))
        }
    }
}

// Function to show DatePicker

private fun showDateTimePicker(context: Context, initialDateTime: Date?, onDateTimeSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    initialDateTime?.let { calendar.time = it }

    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = calendar.get(Calendar.MINUTE)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)

            // Prikaži dijalog za odabir vremena nakon odabira datuma
            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)

                    onDateTimeSelected(calendar.time)
                },
                currentHour,
                currentMinute,
                true
            ).show()
        },
        currentYear,
        currentMonth,
        currentDayOfMonth
    )

    datePickerDialog.show()
}



/*private fun showDatePicker(context: Context, initialDate: Date?, onDateSelected: (Date) -> Unit) {
    val calendar = Calendar.getInstance()
    initialDate?.let { calendar.time = it }

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            onDateSelected(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}*/


@Preview(showBackground = true)
@Composable
fun ShowForms(
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        ConcertForm1(onSaveClicked = {})
    }

}