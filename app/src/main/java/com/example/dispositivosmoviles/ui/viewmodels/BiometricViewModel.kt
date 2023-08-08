package com.example.dispositivosmoviles.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay

class BiometricViewModel : ViewModel() {

    var isLoading = MutableLiveData<Boolean>();
    //simula el tiempo de carga de un proceso

    suspend fun  charginfData(){ //estas funciones no  retornan nada
                                    //  solo actualizan data
        isLoading.postValue(true)
        delay(5000)
        isLoading.postValue(false)
    }

}