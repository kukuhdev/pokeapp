package com.codelite.pokeapp.view.main.response


import com.codelite.pokeapp.api.ApiResult
import com.codelite.pokeapp.api.AppConstants
import com.codelite.pokeapp.api.BaseRepository
import com.codelite.pokeapp.api.RetrofitFactory
import com.codelite.pokeapp.view.main.viewmodel.MainService


/**
 * Created by Kukuh on 01/02/2021.
 * Codelabs Indonesia
 * kukuh@codelabs.co.id
 */
class MainRepository private constructor() : BaseRepository() {
    suspend fun getPokemonsApi(): ApiResult<PokemonListResponse?> {
        return safeApiCall(call = {
            RetrofitFactory.retrofit(AppConstants.url).create(MainService::class.java)
                .getPokemons()
        })
    }

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: MainRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: MainRepository().also { instance = it }
            }
    }
}