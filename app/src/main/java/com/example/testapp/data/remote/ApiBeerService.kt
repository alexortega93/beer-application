package com.example.testapp.data.remote

import com.example.testapp.data.model.BeerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiBeerService {

    @GET("v2/beers")
    suspend fun getBeers(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Response<List<BeerResponse>>
}