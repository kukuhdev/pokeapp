package com.codelite.pokeapp.view.main.viewmodel

import com.codelite.pokeapp.view.main.response.PokemonListResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainService {

    @GET("pokemon")
    suspend fun getPokemons(): Response<PokemonListResponse?>

}