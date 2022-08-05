package com.vila.testcoroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class MainViewModel: ViewModel() {

    fun experimentalCoroutine(){
        viewModelScope.async {

        }
    }

    fun bringRemoteDate(){
        viewModelScope.launch {
            fakeRemoteCall()
        }
    }

    private suspend fun fakeRemoteCall(){
        withContext(Dispatchers.IO){
            delay(3000)
        }
    }
}