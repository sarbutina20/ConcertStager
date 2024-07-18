package hr.foi.air.concertstager.viewmodels.Performer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.Validator
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.PerformerUpdateBody
import hr.foi.air.concertstager.ws.models.response.Genre
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.request_handlers.Genre.GetGenreRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Performer.UpdatePerformerProfileRequestHandler


class EditPerformerProfileViewModel : ViewModel() {
    var originalName: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_name)
    var originalEmail: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_email)

    var name: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_name)
    var email: MutableLiveData<String> = MutableLiveData(UserLoginContext.loggedUser!!.user_email)
    var genreId: MutableLiveData<Int> = MutableLiveData(UserLoginContext.loggedUser!!.user_genreId)
    private val _genres: MutableLiveData<List<Genre>> = MutableLiveData(mutableListOf())
    val genres: LiveData<List<Genre>> = _genres
    private val _genreNames: MutableLiveData<List<String>> = MutableLiveData(mutableListOf())
    val genreNames: LiveData<List<String>> = _genreNames

    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun getGenre(){
        val id = genreId.value
        val requestHandler = GetGenreRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id!!)
        requestHandler.sendRequest(
            object : ResponseListener<Genre> {
                override fun onSuccessfulResponse(response: SuccessfulResponseBody<Genre>) {
                    _genres.value = response.data.toMutableList()
                    _genreNames.value = _genres.value?.map { it.name!! } ?: emptyList()
                    Log.i("USPJEH", "Uspjesno dohvaceni zanrovi" + _genreNames.value)
                }

                override fun onErrorResponse(response: ErrorResponseBody) {
                    _errorMessage.value = response.message
                    Log.i("NEUSPJEH", response.message + " " + response.error_code)
                }

                override fun onNetworkFailure(t: Throwable) {
                    Log.i("INTERNET", "GRESKA NA NETU")
                }
            }
        )
    }

    fun updatePerformer(onSuccess: () -> Unit){
        val performerUpdateBody = PerformerUpdateBody(
            genreId.value!!,
            name.value!!,
            email.value!!
        )

        validateInputs(performerUpdateBody)
        if(_errorMessage.value!!.isEmpty()){
            val requestHandler = UpdatePerformerProfileRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", UserLoginContext.loggedUser!!.userId!!, performerUpdateBody)
            requestHandler.sendRequest(
                object : ResponseListener<Performer> {
                    override fun onSuccessfulResponse(response: SuccessfulResponseBody<Performer>) {
                        originalName.value = performerUpdateBody.name
                        originalEmail.value = performerUpdateBody.email
                        UserLoginContext.loggedUser!!.user_name = originalName.value
                        UserLoginContext.loggedUser!!.user_email = originalEmail.value
                        onSuccess()
                    }

                    override fun onErrorResponse(response: ErrorResponseBody) {
                        _errorMessage.value = response.message
                        Log.i("NEUSPJEH", response.message + " " + response.error_code)
                    }

                    override fun onNetworkFailure(t: Throwable) {
                        Log.i("INTERNET", "GRESKA NA NETU")
                    }
                }
            )
        }
    }

    fun declineUpdate(onSuccess: () -> Unit){
        name.value = originalName.value
        email.value = originalEmail.value
        _errorMessage.value = ""
        onSuccess()
    }

    private fun validateInputs(performerUpdateBody: PerformerUpdateBody){
        var allValidFormat = true
        if(!Validator.validateName(performerUpdateBody.name!!)){
            _errorMessage.value = "Invalid name format"
            allValidFormat = false
        }
        if(!Validator.validateEmail(performerUpdateBody.email!!)){
            _errorMessage.value = "Invalid email format"
            allValidFormat = false
        }

        if(allValidFormat){
            _errorMessage.value = ""
        }
    }
}