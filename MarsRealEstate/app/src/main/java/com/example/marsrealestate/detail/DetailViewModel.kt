package com.example.marsrealestate.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.marsrealestate.R
import com.example.marsrealestate.network.MarsProperty

class DetailViewModel(marsProperty: MarsProperty, application: Application) :
    AndroidViewModel(application) {
    private val _selectedProperty = MutableLiveData<MarsProperty>()
    val selectedProperty: LiveData<MarsProperty>
        get() = _selectedProperty

    val displayPropertyType = Transformations.map(selectedProperty) {
        val type: String = when (it.isRental) {
            true -> application.applicationContext.getString(R.string.type_rent)
            false -> application.applicationContext.getString(R.string.type_sale)
        }
        application.applicationContext.getString(R.string.display_type, type)
    }

    val displayPropertyPrice=Transformations.map(selectedProperty){
        application.applicationContext.getString(when(it.isRental){
            true -> R.string.display_price_monthly_rental
            false -> R.string.display_price
        },it.price)
    }

    init {
        _selectedProperty.value = marsProperty
    }
}