package com.codelite.pokeapp.api

import com.google.gson.annotations.SerializedName

data class ApiResponseError(
    @SerializedName("StatusCode")
    val wtfStatusCode: String,
    @SerializedName("Message")
    val wtfMessage: String
)