package com.codelite.pokeapp.view.detail.viewmodel

import com.codelite.pokeapp.view.detail.response.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("pokemon/{id}")
    suspend fun getDetail(@Path("id") id: String): Response<DetailResponse?>
}