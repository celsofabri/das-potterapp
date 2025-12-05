package com.mob2.daspotterapp.network

import com.mob2.daspotterapp.model.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface HpApiService {
    
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): List<Character>
    
    @GET("characters/staff")
    suspend fun getStaff(): List<Character>
    
    @GET("characters/house/{house}")
    suspend fun getStudentsByHouse(@Path("house") house: String): List<Character>
}
