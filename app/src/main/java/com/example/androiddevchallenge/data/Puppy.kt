package com.example.androiddevchallenge.data

import java.io.Serializable

data class Puppy(
    val id: Int,
    val name: String,
    val age: String,
    val images: List<Int> = emptyList(),
    val des: String = "",
):Serializable
