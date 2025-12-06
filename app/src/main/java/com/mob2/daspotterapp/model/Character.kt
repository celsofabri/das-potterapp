package com.mob2.daspotterapp.model

import com.google.gson.annotations.SerializedName

data class Character(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("alternate_names")
    val alternateNames: List<String>? = null,
    
    @SerializedName("species")
    val species: String? = null,
    
    @SerializedName("gender")
    val gender: String? = null,
    
    @SerializedName("house")
    val house: String? = null,
    
    @SerializedName("dateOfBirth")
    val dateOfBirth: String? = null,
    
    @SerializedName("yearOfBirth")
    val yearOfBirth: Int? = null,
    
    @SerializedName("wizard")
    val wizard: Boolean? = null,
    
    @SerializedName("ancestry")
    val ancestry: String? = null,
    
    @SerializedName("eyeColour")
    val eyeColour: String? = null,
    
    @SerializedName("hairColour")
    val hairColour: String? = null,
    
    @SerializedName("wand")
    val wand: Wand? = null,
    
    @SerializedName("patronus")
    val patronus: String? = null,
    
    @SerializedName("hogwartsStudent")
    val hogwartsStudent: Boolean? = null,
    
    @SerializedName("hogwartsStaff")
    val hogwartsStaff: Boolean? = null,
    
    @SerializedName("actor")
    val actor: String? = null,
    
    @SerializedName("alternate_actors")
    val alternateActors: List<String>? = null,
    
    @SerializedName("alive")
    val alive: Boolean? = null,
    
    @SerializedName("image")
    val image: String? = null
)

data class Wand(
    @SerializedName("wood")
    val wood: String? = null,
    
    @SerializedName("core")
    val core: String? = null,
    
    @SerializedName("length")
    val length: Double? = null
)
