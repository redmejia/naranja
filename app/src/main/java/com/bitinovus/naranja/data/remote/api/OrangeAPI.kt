package com.bitinovus.naranja.data.remote.api

import com.bitinovus.naranja.data.remote.model.Profiles
import retrofit2.Response
import retrofit2.http.GET

interface OrangeAPI {
    @GET("/profiles")
    suspend fun getProfiles(): Response<Profiles>
}