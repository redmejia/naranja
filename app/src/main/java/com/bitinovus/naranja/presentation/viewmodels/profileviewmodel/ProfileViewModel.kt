package com.bitinovus.naranja.presentation.viewmodels.profileviewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.naranja.data.remote.api.OrangeAPI
import com.bitinovus.naranja.data.remote.config.APIConfig
import com.bitinovus.naranja.data.remote.model.Profiles
import com.bitinovus.naranja.data.remote.repository.OrangeApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val apiConfig = APIConfig.createService(OrangeAPI::class.java)

    private val repository = OrangeApiRepository(apiConfig)

    private val _profileState = MutableStateFlow(Profiles())
    val profileState: StateFlow<Profiles> = _profileState.asStateFlow()

    private val _currentPage = mutableIntStateOf(1) // Track current page

    init {
        fetchProfiles()
    }

    private fun fetchProfiles() {
        viewModelScope.launch {
            repository.getProfiles(page = _currentPage.intValue).collect { response ->
                if (response.isSuccessful) {
                    response.body()?.let { profiles ->
                        _profileState.value =
                            Profiles(data = _profileState.value.data + profiles.data)
                    }
                }
            }
        }
    }

    fun onRefresh() {
        _currentPage.intValue += 1
        fetchProfiles() // Fetch next page
    }
}

