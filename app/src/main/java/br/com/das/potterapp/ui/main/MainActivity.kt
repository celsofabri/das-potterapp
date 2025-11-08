package br.com.das.potterapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.das.potterapp.databinding.ActivityMainBinding
import br.com.das.potterapp.ui.characterbyid.CharacterByIdActivity
import br.com.das.potterapp.ui.house.StudentsByHouseActivity
import br.com.das.potterapp.ui.staff.HogwartsStaffActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupClickListeners()
    }
    
    private fun setupClickListeners() {
        binding.btnCharacterById.setOnClickListener {
            startActivity(Intent(this, CharacterByIdActivity::class.java))
        }
        
        binding.btnHogwartsStaff.setOnClickListener {
            startActivity(Intent(this, HogwartsStaffActivity::class.java))
        }
        
        binding.btnStudentsByHouse.setOnClickListener {
            startActivity(Intent(this, StudentsByHouseActivity::class.java))
        }
        
        binding.btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}
