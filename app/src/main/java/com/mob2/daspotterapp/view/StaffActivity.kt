package com.mob2.daspotterapp.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mob2.daspotterapp.R
import com.mob2.daspotterapp.network.HpApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class StaffActivity : AppCompatActivity() {
    
    private lateinit var btnBack: Button
    private lateinit var tvResult: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var apiService: HpApiService
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff)
        
        // Initialize views
        btnBack = findViewById(R.id.btnBack)
        tvResult = findViewById(R.id.tvResult)
        progressBar = findViewById(R.id.progressBar)
        
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        
        apiService = retrofit.create(HpApiService::class.java)
        
        btnBack.setOnClickListener {
            finish()
        }
        
        // Load staff automatically
        loadStaff()
    }
    
    private fun loadStaff() {
        progressBar.visibility = View.VISIBLE
        tvResult.text = getString(R.string.loading)
        
        lifecycleScope.launch {
            try {
                val staff = withContext(Dispatchers.IO) {
                    apiService.getStaff()
                }
                
                if (staff.isNotEmpty()) {
                    val staffNames = staff.joinToString(separator = "\n") { "• ${it.name}" }
                    tvResult.text = staffNames
                } else {
                    tvResult.text = getString(R.string.no_data)
                }
            } catch (e: Exception) {
                tvResult.text = getString(R.string.error_network)
                Toast.makeText(
                    this@StaffActivity,
                    getString(R.string.error_network),
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
