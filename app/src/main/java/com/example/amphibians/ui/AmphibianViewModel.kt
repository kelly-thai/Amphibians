/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibianAPI
import kotlinx.coroutines.launch
import java.lang.Exception

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {

    // Properties to represent MutableLiveData and LiveData for the API status
    private val _status = MutableLiveData<AmphibianApiStatus>()
    val status: LiveData<AmphibianApiStatus>
        get() = _status

    // Properties to represent MutableLiveData and LiveData for a list of amphibian objects
    private val _amphibians = MutableLiveData<List<Amphibian>>()
    val amphibians: LiveData<List<Amphibian>>
        get() = _amphibians

    // Properties to represent MutableLiveData and LiveData for a single amphibian object.
    private val _amphibian = MutableLiveData<Amphibian>()
    val amphibian: LiveData<Amphibian>
        get() = _amphibian

    // Function that gets a list of amphibians from the api service and sets the status via
    // a Coroutine
    fun getAmphibianList(){
        viewModelScope.launch{
            _status.value = AmphibianApiStatus.LOADING
            try {
                _amphibians.value = AmphibianAPI.retrofitService.getAmphibians()
                _status.value = AmphibianApiStatus.DONE
            } catch (e: Exception){
                _amphibians.value = emptyList()
                _status.value = AmphibianApiStatus.ERROR
            }
        }
    }

    // Function to set amphibian to amphibian passed as argument
    fun onAmphibianClicked(amphibian: Amphibian) {
        _amphibian.value = amphibian
    }
}
