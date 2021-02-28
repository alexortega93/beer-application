package com.example.testapp.ui.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.testapp.data.entity.BeerEntity
import com.example.testapp.data.repository.BeerRepository
import com.example.testapp.util.Resource

class BeerViewModel @ViewModelInject constructor(private val beerRepository: BeerRepository) : ViewModel() {

    private val _page = MutableLiveData<Int>()

    private val beerLiveData = _page.switchMap { page ->
        beerRepository.getBeer(page)
    }
    val beer: LiveData<Resource<List<BeerEntity>>> = beerLiveData

    fun start(page: Int) {
        _page.value = page
    }
}