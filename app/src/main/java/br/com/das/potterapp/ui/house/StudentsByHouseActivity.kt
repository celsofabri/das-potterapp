package br.com.das.potterapp.ui.house

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.com.das.potterapp.R
import br.com.das.potterapp.databinding.ActivityStudentsByHouseBinding
import br.com.das.potterapp.ui.CharacterAdapter
import br.com.das.potterapp.util.Result

class StudentsByHouseActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityStudentsByHouseBinding
    private lateinit var viewModel: StudentsByHouseViewModel
    private lateinit var adapter: CharacterAdapter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentsByHouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.students_by_house_title)
        
        viewModel = ViewModelProvider(this)[StudentsByHouseViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }
    
    private fun setupRecyclerView() {
        adapter = CharacterAdapter()
        binding.recyclerView.adapter = adapter
    }
    
    private fun setupClickListeners() {
        binding.btnLoadStudents.setOnClickListener {
            val selectedHouse = getSelectedHouse()
            if (selectedHouse != null) {
                viewModel.getStudentsByHouse(selectedHouse)
            } else {
                Toast.makeText(this, R.string.error_no_house_selected, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun getSelectedHouse(): String? {
        return when (binding.radioGroupHouses.checkedRadioButtonId) {
            R.id.rbGryffindor -> "gryffindor"
            R.id.rbSlytherin -> "slytherin"
            R.id.rbRavenclaw -> "ravenclaw"
            R.id.rbHufflepuff -> "hufflepuff"
            else -> null
        }
    }
    
    private fun setupObservers() {
        viewModel.students.observe(this) { result ->
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
