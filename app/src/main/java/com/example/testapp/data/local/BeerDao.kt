package com.example.testapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testapp.data.entity.BeerEntity

@Dao
interface BeerDao {
    @Query("SELECT * FROM world_beer")
    fun getAll(): LiveData<List<BeerEntity>>

    @Query("SELECT * FROM world_beer WHERE id = :id")
    fun getBeer(id: String): LiveData<BeerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(beers: List<BeerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(beer: BeerEntity)

    @Query("SELECT * FROM world_beer")
    suspend fun getAllForRequest(): List<BeerEntity>


}