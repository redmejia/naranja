package com.bitinovus.naranja.data.remote.model

data class Profiles(
    val data: List<Profile> = emptyList(),
)

data class Profile(
    val name: String,
    val hobbies: List<String>,
    val image: String,
)
