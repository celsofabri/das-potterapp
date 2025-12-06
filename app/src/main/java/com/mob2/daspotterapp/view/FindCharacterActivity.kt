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
    private lateinit var progressBar: ProgressBar
    private lateinit var tvCharacterResult: TextView
    private lateinit var tvError: TextView
    private lateinit var apiService: HpApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_character)

        // Initialize views
        etCharacterId = findViewById(R.id.etCharacterId)
        btnSearch = findViewById(R.id.btnSearch)
        btnBack = findViewById(R.id.btnBack)
        progressBar = findViewById(R.id.progressBar)
        tvCharacterResult = findViewById(R.id.tvCharacterResult)
        tvError = findViewById(R.id.tvError)

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
        tvCharacterResult.visibility = View.GONE
        tvError.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val character = withContext(Dispatchers.IO) {
                    apiService.getCharacterById(id)
                }

                if (character.isNotEmpty()) {
                    val char = character[0]
                    // Exibir apenas name e house conforme especificação
                    val result = buildString {
                        append("Nome: ${char.name}")
                        if (!char.house.isNullOrEmpty()) {
                            append("\nCasa: ${char.house}")
                        }
                    }
                    tvCharacterResult.text = result
                    tvCharacterResult.visibility = View.VISIBLE
                } else {
                    tvError.text = getString(R.string.error_character_not_found)
                    tvError.visibility = View.VISIBLE
                }
            } catch (e: Exception) {
                tvError.text = getString(R.string.error_network)
                tvError.visibility = View.VISIBLE
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
