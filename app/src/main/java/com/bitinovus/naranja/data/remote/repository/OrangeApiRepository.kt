package com.bitinovus.naranja.data.remote.repository

import android.util.Log
import com.bitinovus.naranja.data.remote.api.OrangeAPI
import com.bitinovus.naranja.data.remote.model.Profiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class OrangeApiRepository(
    private val orangeAPI: OrangeAPI,
) {

    fun getProfiles(page: Int = 1, limit: Int = 2): Flow<Response<Profiles>> = flow {
        try {
            emit(orangeAPI.getProfiles(page, limit))
        } catch (e: Exception) {
            Log.d("ERROR", "getProfiles: $e")
        }
    }.flowOn(Dispatchers.IO)

}