package com.filizuzulmez.turkiyefinans2.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchAPIService {

    companion object {
        val baseURL = "https://itunes.apple.com/"

        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }
}