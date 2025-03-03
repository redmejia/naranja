package com.bitinovus.naranja.data.remote.model

data class Profiles(
    val data: List<Profile> = emptyList(),
    val limit: Long = 0,
    val page: Long = 0,
    val total: Long = 0,
    val totalPages: Long = 0,
)

data class Profile(
    val name: String,
    val hobbies: List<String>,
    val image: String,
)
