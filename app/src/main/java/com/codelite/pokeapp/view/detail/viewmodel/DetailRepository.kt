package com.codelite.pokeapp.view.detail.viewmodel

import com.codelite.pokeapp.api.ApiResult
import com.codelite.pokeapp.api.AppConstants
import com.codelite.pokeapp.api.BaseRepository
import com.codelite.pokeapp.api.RetrofitFactory
import com.codelite.pokeapp.view.detail.response.DetailResponse

/**
 * Created by Kukuh on 01/02/2021.
 * Codelabs Indonesia
 * kukuh@codelabs.co.id
 */
class DetailRepository private constructor() : BaseRepository() {
    suspend fun getDetailApi(id: String): ApiResult<DetailResponse?> {
        return safeApiCall(call = {
            RetrofitFactory.retrofit(AppConstants.url).create(DetailService::class.java)
                .getDetail(id)
        })
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: DetailRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: DetailRepository().also { instance = it }
            }
    }
}