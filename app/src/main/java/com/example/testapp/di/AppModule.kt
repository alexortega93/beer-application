package com.example.testapp.di

import android.content.Context
import com.example.testapp.data.local.AppDatabase
import com.example.testapp.data.remote.BeerRemoteDataSource
import com.example.testapp.data.local.BeerDao
import com.example.testapp.data.remote.ApiBeerService
import com.example.testapp.data.repository.BeerRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.punkapi.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideBeerService(retrofit: Retrofit): ApiBeerService = retrofit.create(ApiBeerService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(apiBeerService: ApiBeerService) = BeerRemoteDataSource(apiBeerService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.beerDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: BeerRemoteDataSource,
                          localDataSource: BeerDao) =
        BeerRepository(remoteDataSource, localDataSource)
}