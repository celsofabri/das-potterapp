package com.mob2.daspotterapp.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mob2.daspotterapp.R
import com.mob2.daspotterapp.model.Character
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
    private lateinit var llCharacterDetails: LinearLayout
    private lateinit var tvError: TextView
    
    // Character detail views
    private lateinit var ivCharacterImage: ImageView
    private lateinit var tvCharacterName: TextView
    private lateinit var tvCharacterHouse: TextView
    private lateinit var tvCharacterActor: TextView
    private lateinit var tvCharacterSpecies: TextView
    private lateinit var tvCharacterGender: TextView
    private lateinit var tvCharacterDateOfBirth: TextView
    private lateinit var tvCharacterAncestry: TextView
    private lateinit var tvCharacterEyeColour: TextView
    private lateinit var tvCharacterHairColour: TextView
    private lateinit var tvCharacterPatronus: TextView
    private lateinit var tvCharacterWand: TextView
    private lateinit var tvCharacterWizard: TextView
    private lateinit var tvCharacterAlive: TextView
    
    private lateinit var apiService: HpApiService
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_character)
        
        // Initialize views
        etCharacterId = findViewById(R.id.etCharacterId)
        btnSearch = findViewById(R.id.btnSearch)
        btnBack = findViewById(R.id.btnBack)
        progressBar = findViewById(R.id.progressBar)
        llCharacterDetails = findViewById(R.id.llCharacterDetails)
        tvError = findViewById(R.id.tvError)
        
        // Character detail views
        ivCharacterImage = findViewById(R.id.ivCharacterImage)
        tvCharacterName = findViewById(R.id.tvCharacterName)
        tvCharacterHouse = findViewById(R.id.tvCharacterHouse)
        tvCharacterActor = findViewById(R.id.tvCharacterActor)
        tvCharacterSpecies = findViewById(R.id.tvCharacterSpecies)
        tvCharacterGender = findViewById(R.id.tvCharacterGender)
        tvCharacterDateOfBirth = findViewById(R.id.tvCharacterDateOfBirth)
        tvCharacterAncestry = findViewById(R.id.tvCharacterAncestry)
        tvCharacterEyeColour = findViewById(R.id.tvCharacterEyeColour)
        tvCharacterHairColour = findViewById(R.id.tvCharacterHairColour)
        tvCharacterPatronus = findViewById(R.id.tvCharacterPatronus)
        tvCharacterWand = findViewById(R.id.tvCharacterWand)
        tvCharacterWizard = findViewById(R.id.tvCharacterWizard)
        tvCharacterAlive = findViewById(R.id.tvCharacterAlive)
        
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
        llCharacterDetails.visibility = View.GONE
        tvError.visibility = View.GONE
        
        lifecycleScope.launch {
            try {
                val characters = withContext(Dispatchers.IO) {
                    apiService.getCharacterById(id)
                }
                
                if (characters.isNotEmpty()) {
                    displayCharacter(characters[0])
                } else {
                    showError(getString(R.string.error_character_not_found))
                }
            } catch (e: Exception) {
                showError(getString(R.string.error_network))
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
    
    private fun displayCharacter(character: Character) {
        llCharacterDetails.visibility = View.VISIBLE
        tvError.visibility = View.GONE
        
        // Load image
        if (!character.image.isNullOrEmpty()) {
            Glide.with(this)
                .load(character.image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivCharacterImage)
        } else {
            ivCharacterImage.setImageResource(R.drawable.ic_launcher_foreground)
        }
        
        // Name
        tvCharacterName.text = character.name
        
        // House
        if (!character.house.isNullOrEmpty()) {
            tvCharacterHouse.text = character.house
            tvCharacterHouse.visibility = View.VISIBLE
        } else {
            tvCharacterHouse.visibility = View.GONE
        }
        
        // Actor
        setTextOrHide(tvCharacterActor, character.actor, "Ator: ")
        
        // Species
        setTextOrHide(tvCharacterSpecies, character.species, "Espécie: ")
        
        // Gender
        setTextOrHide(tvCharacterGender, character.gender, "Gênero: ")
        
        // Date of Birth
        setTextOrHide(tvCharacterDateOfBirth, character.dateOfBirth, "Data de Nascimento: ")
        
        // Ancestry
        setTextOrHide(tvCharacterAncestry, character.ancestry, "Ancestralidade: ")
        
        // Eye Colour
        setTextOrHide(tvCharacterEyeColour, character.eyeColour, "Cor dos Olhos: ")
        
        // Hair Colour
        setTextOrHide(tvCharacterHairColour, character.hairColour, "Cor do Cabelo: ")
        
        // Patronus
        setTextOrHide(tvCharacterPatronus, character.patronus, "Patrono: ")
        
        // Wand
        if (character.wand != null && 
            (!character.wand.wood.isNullOrEmpty() || 
             !character.wand.core.isNullOrEmpty() || 
             character.wand.length != null)) {
            val wandInfo = buildString {
                append("Varinha: ")
                val parts = mutableListOf<String>()
                character.wand.wood?.let { parts.add(it) }
                character.wand.length?.let { parts.add("$it polegadas") }
                character.wand.core?.let { parts.add(it) }
                append(parts.joinToString(", "))
            }
            tvCharacterWand.text = wandInfo
            tvCharacterWand.visibility = View.VISIBLE
        } else {
            tvCharacterWand.visibility = View.GONE
        }
        
        // Wizard
        if (character.wizard != null) {
            tvCharacterWizard.text = "Bruxo: ${if (character.wizard) "Sim" else "Não"}"
            tvCharacterWizard.visibility = View.VISIBLE
        } else {
            tvCharacterWizard.visibility = View.GONE
        }
        
        // Alive
        if (character.alive != null) {
            tvCharacterAlive.text = "Vivo: ${if (character.alive) "Sim" else "Não"}"
            tvCharacterAlive.visibility = View.VISIBLE
        } else {
            tvCharacterAlive.visibility = View.GONE
        }
    }
    
    private fun setTextOrHide(textView: TextView, value: String?, prefix: String = "") {
        if (!value.isNullOrEmpty()) {
            textView.text = "$prefix$value"
            textView.visibility = View.VISIBLE
        } else {
            textView.visibility = View.GONE
        }
    }
    
    private fun showError(message: String) {
        llCharacterDetails.visibility = View.GONE
        tvError.text = message
        tvError.visibility = View.VISIBLE
    }
}
