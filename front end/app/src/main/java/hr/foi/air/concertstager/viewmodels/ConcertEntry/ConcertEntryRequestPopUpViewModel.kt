package hr.foi.air.concertstager.viewmodels.ConcertEntry

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.foi.air.concertstager.context.UserLoginContext
import hr.foi.air.concertstager.core.login.network.ResponseListener
import hr.foi.air.concertstager.core.login.network.models.ErrorResponseBody
import hr.foi.air.concertstager.core.login.network.models.SuccessfulResponseBody
import hr.foi.air.concertstager.ws.models.ConcertEntryUpdateBody
import hr.foi.air.concertstager.ws.models.response.ConcertEntry
import hr.foi.air.concertstager.ws.models.response.Performer
import hr.foi.air.concertstager.ws.request_handlers.ConcertEntry.AcceptDenyConcertEntryRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.ConcertEntry.GetUnresolvedConcertEntriesRequestHandler
import hr.foi.air.concertstager.ws.request_handlers.Performer.GetPerformersRequestHandler

class ConcertEntryRequestPopUpViewModel: ViewModel() {
    private val _unresolvedEntries : MutableLiveData<List<ConcertEntry>> = MutableLiveData(
        mutableListOf())
    val unresolvedEntries : MutableLiveData<List<ConcertEntry>> = _unresolvedEntries

    private val _allPerformers : MutableLiveData<List<Performer>> = MutableLiveData(mutableListOf())
    val allPerformers : MutableLiveData<List<Performer>> = _allPerformers

    private val _errorMessage : MutableLiveData<String> = MutableLiveData("")
    val errorMessage : MutableLiveData<String> = _errorMessage

    fun getAllPerformers(){
        val getPerformersRequestHandler = GetPerformersRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}")
        getPerformersRequestHandler.sendRequest(object: ResponseListener<Performer>{
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<Performer>) {
                Log.i("USPJEH", response.data.toString())
                _allPerformers.value = response.data.toMutableList()
                _errorMessage.value = ""
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure...")
                _errorMessage.value = t.message
            }
        })
    }

    fun acceptDenyEntry(id: Int, concertEntryUpdateBody: ConcertEntryUpdateBody){
        val acceptDenyConcertEntry = AcceptDenyConcertEntryRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", id, concertEntryUpdateBody)
        acceptDenyConcertEntry.sendRequest(object: ResponseListener<ConcertEntry> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<ConcertEntry>) {
                _errorMessage.value = ""
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure...")
                _errorMessage.value = t.message
            }
        })
    }

    fun getUnresolvedEntries(concertId: Int){
        val getUnresolvedConcertEntriesRequestHandler = GetUnresolvedConcertEntriesRequestHandler("Bearer ${UserLoginContext.loggedUser!!.jwt}", concertId)
        getUnresolvedConcertEntriesRequestHandler.sendRequest(object:
            ResponseListener<ConcertEntry> {
            override fun onSuccessfulResponse(response: SuccessfulResponseBody<ConcertEntry>) {
                Log.i("USPJEH", response.data.toString())
                _unresolvedEntries.value = response.data.toMutableList()
                _errorMessage.value = ""
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                Log.i("NEUSPJEH", response.message)
                _errorMessage.value = response.message
            }

            override fun onNetworkFailure(t: Throwable) {
                Log.i("NETWORK", "Network failure...")
                _errorMessage.value = t.message
            }
        })
    }
}