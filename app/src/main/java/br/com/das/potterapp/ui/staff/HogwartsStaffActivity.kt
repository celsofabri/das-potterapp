package br.com.das.potterapp.ui.staff

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.das.potterapp.R
import br.com.das.potterapp.databinding.ActivityHogwartsStaffBinding
import br.com.das.potterapp.ui.CharacterAdapter
import br.com.das.potterapp.util.Result

class HogwartsStaffActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityHogwartsStaffBinding
    private lateinit var viewModel: HogwartsStaffViewModel
    private lateinit var adapter: CharacterAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHogwartsStaffBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.hogwarts_staff_title)
        
        viewModel = ViewModelProvider(this)[HogwartsStaffViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        adapter = CharacterAdapter()
        binding.recyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        binding.btnLoadStaff.setOnClickListener {
            viewModel.getHogwartsStaff()
        }
    }
    
    private fun setupObservers() {
        viewModel.staff.observe(this) { result ->
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
        binding.recyclerView.visibility = View.GONE
        binding.tvError.visibility = View.GONE
    }
    
    private fun showSuccess(characters: List<br.com.das.potterapp.data.model.Character>) {
        binding.progressBar.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        
        if (characters.isNotEmpty()) {
            adapter.submitList(characters)
            binding.recyclerView.visibility = View.VISIBLE
        } else {
            showError(getString(R.string.no_data))
        }
    }
    
    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.GONE
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }
    
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
