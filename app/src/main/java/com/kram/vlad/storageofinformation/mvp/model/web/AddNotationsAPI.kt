package com.kram.vlad.storageofinformation.mvp.model.web

import com.kram.vlad.storageofinformation.Constants
import com.kram.vlad.storageofinformation.mvp.model.web.pojo.RESTModels
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by vlad on 10.02.2018.
 * This interface add notation to database by rest api
 */
interface AddNotationsAPI {
    @GET("/add")
    fun addNotations (@Query("email" ) email: String,
               @Query("password") password: String,
               @Query("notation") notations: String): Call<RESTModels.NotationAddResponse>

    companion object Factory {
        fun create(): AddNotationsAPI {
            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Constants.BASE_URL + "/")
                    .build()

            return retrofit.create(AddNotationsAPI::class.java);
        }
    }
}