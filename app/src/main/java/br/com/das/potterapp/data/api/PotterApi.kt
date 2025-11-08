package br.com.das.potterapp.data.api

import br.com.das.potterapp.data.model.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface PotterApi {
    
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): List<Character>
    
    @GET("characters/staff")
    suspend fun getHogwartsStaff(): List<Character>
    
    @GET("characters/house/{house}")
    suspend fun getCharactersByHouse(@Path("house") house: String): List<Character>
}
