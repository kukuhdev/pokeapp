package com.codelite.pokeapp.view.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.codelite.pokeapp.api.ApiCallback
import com.codelite.pokeapp.api.ApiResult
import com.codelite.pokeapp.view.detail.response.DetailResponse
import kotlinx.coroutines.cancel

class DetailViewModel internal constructor(private val detailRepository: DetailRepository) :
    ViewModel() {

    fun getDetail(id: String): LiveData<ApiCallback<DetailResponse>> =
        liveData(context = viewModelScope.coroutineContext) {
            emit(ApiCallback.OnLoading("loading"))
            when (val callApi = detailRepository.getDetailApi(id)) {
                is ApiResult.Success -> {
                    if (callApi.data?.id != 0) {
                        emit(ApiCallback.OnSuccess(callApi.data))
                    } else {
                        //var errorMsg = callApi.data?.message
                        var errorMsg = "Maaf, Terjadi kesalahan"
                        if (errorMsg.isNullOrEmpty())
                            errorMsg = "Maaf, Terjadi kesalahan"

                        emit(
                            ApiCallback.OnError(
                                "Maaf, Terjadi kesalahan",
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