package com.bitinovus.naranja.data.remote.api

import com.bitinovus.naranja.data.remote.model.Profiles
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OrangeAPI {
    @GET("/profiles")
    suspend fun getProfiles(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<Profiles>
}