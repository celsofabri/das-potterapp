package br.com.das.potterapp.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    @Json(name = "id")
    val id: String,
    
    @Json(name = "name")
    val name: String,
    
    @Json(name = "house")
    val house: String,
    
    @Json(name = "actor")
    val actor: String? = null,
    
    @Json(name = "image")
    val image: String? = null
)
