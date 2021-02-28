package com.example.testapp.data.remote

import javax.inject.Inject

class BeerRemoteDataSource @Inject constructor(
    private val beerService: ApiBeerService
): BaseDataSource() {

    suspend fun getBeer(page: Int) = getResult{ beerService.getBeers(page, 20) }
}