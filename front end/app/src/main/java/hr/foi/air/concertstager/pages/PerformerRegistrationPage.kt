package hr.foi.air.concertstager.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ui.components.NextPageButton
import hr.foi.air.concertstager.ui.components.PreviousPageButton
import hr.foi.air.concertstager.ui.components.RoundedBackgroundBox
import hr.foi.air.concertstager.ui.components.StyledComboBox
import hr.foi.air.concertstager.ui.components.StyledOneLineTextField
import hr.foi.air.concertstager.ws.models.Genre
import hr.foi.air.concertstager.ws.request_handlers.Genre.FetchGenreRequestHandler

@Composable
fun PerformerRegistrationPage(
    modifier: Modifier = Modifier,
    returnToPreviousPage: () -> Unit,
    continueToNextPage: () -> Unit,
){
    var genresRaw by remember { mutableStateOf(emptyList<Genre>()) }
    var genres by remember { mutableStateOf(emptyList<String>()) }
    var genreNames by remember { mutableStateOf(emptyList<String>()) }
    var selectedGenre by remember { mutableStateOf("") }
    var selectedGenreId : Int

    DisposableEffect(Unit) {
        try {
            val fetchGenreRequestHandler = FetchGenreRequestHandler()
            fetchGenreRequestHandler.sendRequest(object : ResponseListener<Genre> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<Genre>) {
                    Log.d("PerformerManualRegistrationPage", "Genres fetched!")
                    genresRaw = response.data.toList()
                    genreNames = genresRaw.map { it.name }
                    genres = genreNames
                    selectedGenre = genreNames.firstOrNull().toString()
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    Log.d("PerformerManualRegistrationPage", "Error while fetching genres: ${response.message}")
                }

                override fun onNetworkFailure(t: Throwable) {
                    Log.d("PerformerManualRegistrationPage", "Network error while fetching genres: $t")
                }
            })
        } catch (e: Exception) {
            Log.e("PerformerManualRegistrationPage", "Exception while fetching genres: $e")
        }

        onDispose { }
    }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        RoundedBackgroundBox(fontSize = 48, contentText = "Welcome to Concert Stager!")
        StyledComboBox(
            label = "Genre:",
            items = genres,
            selectedItem = selectedGenre,
            onItemSelected = {selectedGenreItem ->
                selectedGenre = selectedGenreItem
                selectedGenreId = genresRaw.firstOrNull {genre -> genre.name == selectedGenre}?.id!!
                UserLoginContext.loggedUser?.user_genreId = selectedGenreId
            }
        )


        Row(
            modifier = modifier
                .padding(start = 40.dp, end = 40.dp)
                .fillMaxWidth()
                .align(Alignment.Start),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PreviousPageButton(
                onClick = { returnToPreviousPage() }
            )

            Spacer(modifier = Modifier.weight(1f))

            NextPageButton(
                onClick = { continueToNextPage() }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ShowPerformerRegistrationPage(
    modifier: Modifier = Modifier
){
    PerformerRegistrationPage(
        returnToPreviousPage = {},
        continueToNextPage = {}
    )

}