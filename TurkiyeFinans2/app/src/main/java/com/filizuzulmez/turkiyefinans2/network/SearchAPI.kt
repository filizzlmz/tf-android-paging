package com.filizuzulmez.turkiyefinans2.network

import com.filizuzulmez.turkiyefinans2.model.ResponseSearch
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPI {

    @GET("search?")
    suspend fun getDataFromAPI(@Query("term") term: String,@Query("limit") limit: Int): ResponseSearch
}