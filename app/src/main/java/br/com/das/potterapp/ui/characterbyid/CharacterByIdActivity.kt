package br.com.das.potterapp.ui.characterbyid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.das.potterapp.R
import br.com.das.potterapp.databinding.ActivityCharacterByIdBinding
import br.com.das.potterapp.util.Result
import coil.load
import coil.transform.CircleCropTransformation

class CharacterByIdActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityCharacterByIdBinding
    private lateinit var viewModel: CharacterByIdViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterByIdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.character_by_id_title)
        
        viewModel = ViewModelProvider(this)[CharacterByIdViewModel::class.java]
        
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnSearch.setOnClickListener {
            val id = binding.etCharacterId.text.toString().trim()
            if (id.isEmpty()) {
                Toast.makeText(this, R.string.error_empty_id, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.getCharacterById(id)
            }
        }
    }
    
    private fun setupObservers() {
        viewModel.character.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading()
                }
                is Result.Success -> {
                    showSuccess(result.data)
                }
                is Result.Error -> {
                    showError(result.message)
                }
            }
        }
    }
    
    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.scrollView.visibility = View.GONE
        binding.tvError.visibility = View.GONE
    }
    
    private fun showSuccess(characters: List<br.com.das.potterapp.data.model.Character>) {
        binding.progressBar.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        
        if (characters.isNotEmpty()) {
            val character = characters[0]
            val resultText = buildString {
                append("${getString(R.string.label_name)} ${character.name}\n")
                append("${getString(R.string.label_house)} ${character.house.ifEmpty { "Não informada" }}")
            }
            binding.tvResult.text = resultText
            
            // Load character image
            binding.ivCharacter.load(character.image) {
                crossfade(true)
                placeholder(R.drawable.ic_person_placeholder)
                error(R.drawable.ic_person_placeholder)
                transformations(CircleCropTransformation())
            }
            
            binding.scrollView.visibility = View.VISIBLE
        } else {
            showError(getString(R.string.no_data))
        }
    }
    
    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.GONE
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
