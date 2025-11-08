package br.com.das.potterapp.data.repository

import br.com.das.potterapp.data.api.RetrofitClient
import br.com.das.potterapp.data.model.Character
import br.com.das.potterapp.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PotterRepository {
    
    private val api = RetrofitClient.api
    
    suspend fun getCharacterById(id: String): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getCharacterById(id)
                if (response.isNotEmpty()) {
                    Result.Success(response)
                } else {
                    Result.Error("Personagem não encontrado")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Erro ao buscar personagem")
            }
        }
    }
    
    suspend fun getHogwartsStaff(): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getHogwartsStaff()
                if (response.isNotEmpty()) {
                    Result.Success(response)
                } else {
                    Result.Error("Nenhum professor encontrado")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Erro ao buscar professores")
            }
        }
    }
    
    suspend fun getCharactersByHouse(house: String): Result<List<Character>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getCharactersByHouse(house)
                if (response.isNotEmpty()) {
                    Result.Success(response)
                } else {
                    Result.Error("Nenhum estudante encontrado para esta casa")
                }
            } catch (e: Exception) {
                Result.Error(e.message ?: "Erro ao buscar estudantes")
            }
        }
    }
}
