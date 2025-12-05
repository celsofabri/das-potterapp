package com.mob2.daspotterapp.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("house")
    val house: String,
    
    @SerializedName("actor")
    val actor: String? = null,
    
    @SerializedName("image")
    val image: String? = null
)
