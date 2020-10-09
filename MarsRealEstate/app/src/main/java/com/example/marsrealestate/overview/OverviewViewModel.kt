package com.example.marsrealestate.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.marsrealestate.network.MarsApi
import com.example.marsrealestate.network.MarsApiFilter
import com.example.marsrealestate.network.MarsProperty
import kotlinx.coroutines.*

import retrofit2.Response
import java.lang.Exception

enum class MarsApiStatus { LOADING, ERROR, DONE }
class OverviewViewModel : ViewModel() {
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _status = MutableLiveData<MarsApiStatus>()
    val status: LiveData<MarsApiStatus>
        get() = _status


    private val _properties = MutableLiveData<List<MarsProperty>>()
    val properties: LiveData<List<MarsProperty>>
        get() = _properties

    private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
    val navigateToSelectedProperty: LiveData<MarsProperty>
        get() = _navigateToSelectedProperty

    init {
        getMarsRealEstateProperties(hashMapOf("filter" to MarsApiFilter.SHOW_ALL.value))
    }

    fun displayPropertyDetails(marsProperty: MarsProperty) {
        _navigateToSelectedProperty.value = marsProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    private fun getMarsRealEstateProperties(filter: Map<String, String>) {

        viewModelScope.launch {
            try {
                _status.value = MarsApiStatus.LOADING
                val listResult = MarsApi.retrofitService.getProperties(filter)
                _status.value = MarsApiStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }


//        MarsApi.retrofitService.getProperties().enqueue(object : Callback<List<MarsProperty>> {
//            override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
//                _response.value = "Failure${t.message}"
//            }
//
//            override fun onResponse(
//                call: Call<List<MarsProperty>>,
//                response: Response<List<MarsProperty>>
//            ) {
//                _response.value = "Success: ${response.body()?.size} Mars properties retrieved"
//
//            }
//        })

    }

    fun updateFilter(filter: Map<String, String>) {
        getMarsRealEstateProperties(filter)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}