package com.bitinovus.naranja.presentation.viewmodels.profileviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.naranja.data.remote.api.OrangeAPI
import com.bitinovus.naranja.data.remote.config.APIConfig
import com.bitinovus.naranja.data.remote.model.Profile
import com.bitinovus.naranja.data.remote.model.Profiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val apiConfig = APIConfig.createService(OrangeAPI::class.java)

    private val _profileState = MutableStateFlow(Profiles())
    val profileState: StateFlow<Profiles> = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val response = apiConfig.getProfiles()
                if (response.isSuccessful) {
                    response.body()?.let { profiles ->
                        _profileState.value = Profiles(profiles.data)
                    }
                }
            } catch (e: Exception) {
                Log.e("ERROR", "NET: ${e.message}")
            }
        }
    }

    fun delete(profile: Profile) {
        _profileState.value = Profiles(_profileState.value.data - profile)
    }

}