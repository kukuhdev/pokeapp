package com.codelite.pokeapp.api

import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException


open class BaseRepository{

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T?>): ApiResult<T?> {
        return safeApiResult(call)
    }

    private suspend fun <T: Any> safeApiResult(call: suspend ()-> Response<T?>) : ApiResult<T?> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                ApiResult.Success(response.body())
            }else {
                handleResponseCode(response)
            }
        }catch (e:Throwable) {

            e.printStackTrace()
//            FirebaseCrashlytics.getInstance().recordException(e)

            if (!e.message.isNullOrEmpty()) {

                when (e.cause) {
                    is IOException -> {
                        ApiResult.Error("No internet available", 555)
                    }
                    is IllegalStateException -> {
                        ApiResult.Error(e.message, 666)
                    }
                    else -> {
                        ApiResult.Error(e.message, 777)
                    }
                }
            }else{
                ApiResult.Error(e.cause.toString(), 888)
            }
        }
    }

    private fun <T: Any> handleResponseCode(response: Response<T?>): ApiResult<T?> {
        return when (response.code()) {
            404 -> {
                ApiResult.Error(
                    "Data 404",
                    404
                )
            }
            500 -> {
                ApiResult.Error(
                    "An error occurred on server 500",
                    500
                )
            }
            else -> {
                val errorBody = response.errorBody()
                if(errorBody != null) {
                    val errorResponse =
                        Gson().fromJson(errorBody.string(), ApiResponseError::class.java)

                    ApiResult.Error(
                        errorResponse.wtfMessage,
                        response.code()
                    )
                }else{
                    ApiResult.Error(
                        "An error occurred unknown",
                        response.code()
                    )
                }
            }
        }
    }
}