package com.mob2.daspotterapp.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mob2.daspotterapp.R
import com.mob2.daspotterapp.network.HpApiService
import com.mob2.daspotterapp.util.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FindCharacterActivity : AppCompatActivity() {
    
    private lateinit var etCharacterId: EditText
    private lateinit var btnSearch: Button
    private lateinit var btnBack: Button
    private lateinit var tvResult: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var apiService: HpApiService
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_character)
        
        // Initialize views
        etCharacterId = findViewById(R.id.etCharacterId)
        btnSearch = findViewById(R.id.btnSearch)
        btnBack = findViewById(R.id.btnBack)
        tvResult = findViewById(R.id.tvResult)
        progressBar = findViewById(R.id.progressBar)
        
        apiService = RetrofitFactory.getInstance().create(HpApiService::class.java)
        
        btnSearch.setOnClickListener {
            val characterId = etCharacterId.text.toString().trim()
            
            if (characterId.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_id, Toast.LENGTH_SHORT).show()
            } else {
                searchCharacter(characterId)
            }
        }
        
        btnBack.setOnClickListener {
            finish()
        }
    }
    
    private fun searchCharacter(id: String) {
        progressBar.visibility = View.VISIBLE
        tvResult.text = getString(R.string.loading)
        
        lifecycleScope.launch {
            try {
                val characters = withContext(Dispatchers.IO) {
                    apiService.getCharacterById(id)
                }
                
                if (characters.isNotEmpty()) {
                    val character = characters[0]
                    val result = buildString {
                        append("Nome: ${character.name}\n")
                        append("Casa: ${character.house.ifEmpty { "Não informada" }}")
                    }
                    tvResult.text = result
                } else {
                    tvResult.text = getString(R.string.error_character_not_found)
                }
            } catch (e: Exception) {
                tvResult.text = getString(R.string.error_network)
                Toast.makeText(
                    this@FindCharacterActivity,
                    getString(R.string.error_network),
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
