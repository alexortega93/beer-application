package com.example.testapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.testapp.data.local.BeerDao
import com.example.testapp.data.remote.BeerRemoteDataSource
import com.example.testapp.util.Resource
import com.example.testapp.util.Status
import com.example.testapp.util.Utils
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class BeerRepository @Inject constructor(
    private val remoteDataSource: BeerRemoteDataSource,
    private val localDataSource: BeerDao) {

    fun getBeer(page: Int) = performGetOperation(
        databaseQuery = { localDataSource.getAll() },
        networkCall = { remoteDataSource.getBeer(page) },
        saveCallResult = { localDataSource.insertAll(Utils.convertResponseToEntity(it)) }
    )

    fun getBeerDetail(id: String) = performLocalSourceOperation(
        databaseQuery = { localDataSource.getBeer(id) }
    )

    private fun <T, A> performGetOperation(databaseQuery: () -> LiveData<T>,
                                           networkCall: suspend () -> Resource<A>,
                                           saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
            liveData(Dispatchers.IO) {
                emit(Resource.loading())
                val source = databaseQuery.invoke().map { Resource.success(it) }
                emitSource(source)

                val responseStatus = networkCall.invoke()
                if (responseStatus.status == Status.SUCCESS) {
                    saveCallResult(responseStatus.data!!)

                } else if (responseStatus.status == Status.ERROR) {
                    emit(Resource.error(responseStatus.message!!))
                    emitSource(source)
                }
            }

    private fun <T> performLocalSourceOperation(databaseQuery: () -> LiveData<T>): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())
            val source = databaseQuery.invoke().map { Resource.success(it) }
            emitSource(source)
        }
}