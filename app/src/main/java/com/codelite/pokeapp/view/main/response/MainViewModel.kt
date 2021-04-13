package com.codelite.pokeapp.view.main.response

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.codelite.pokeapp.api.ApiCallback
import com.codelite.pokeapp.api.ApiResult
import kotlinx.coroutines.cancel

class MainViewModel internal constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    fun getPokemons(): LiveData<ApiCallback<PokemonListResponse>> =
        liveData(context = viewModelScope.coroutineContext) {
            emit(ApiCallback.OnLoading("loading"))
            when (val callApi = mainRepository.getPokemonsApi()) {
                is ApiResult.Success -> {
                    if (callApi.data != null) {
                        //DataManager.getInstance().setLogin(callApi.data.dATA!!)
                        emit(ApiCallback.OnSuccess(callApi.data))
                    } else {
                        var errorMsg = callApi.message
                        if (errorMsg.isNullOrEmpty())
                            errorMsg = "Maaf, Terjadi kesalahan"

                        emit(
                            ApiCallback.OnError(
                                callApi.message.toString(),
                                errorMsg
                            )
                        )
                    }
                }
                is ApiResult.Error -> {
                    emit(ApiCallback.OnError(callApi.status.toString(), callApi.message))
                }
            }
        }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}