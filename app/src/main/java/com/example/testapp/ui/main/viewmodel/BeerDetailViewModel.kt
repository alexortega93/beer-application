package com.example.testapp.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.testapp.data.entity.BeerEntity
import com.example.testapp.data.repository.BeerRepository
import com.example.testapp.util.Resource

class BeerDetailViewModel @ViewModelInject constructor(private val beerRepository: BeerRepository) : ViewModel() {

    private val _id = MutableLiveData<String>()

    private val beerLiveData = _id.switchMap { id ->
        beerRepository.getBeerDetail(id)
    }
    val id: LiveData<Resource<BeerEntity>> = beerLiveData


    fun start(id: String) {
        _id.value = id
    }
}