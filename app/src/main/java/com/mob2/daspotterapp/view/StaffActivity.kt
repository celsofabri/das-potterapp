package com.mob2.daspotterapp.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mob2.daspotterapp.R
import com.mob2.daspotterapp.adapter.CharacterAdapter
import com.mob2.daspotterapp.network.HpApiService
import com.mob2.daspotterapp.util.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StaffActivity : AppCompatActivity() {

    private lateinit var rvStaff: RecyclerView
    private lateinit var btnBack: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var apiService: HpApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff)

        // Initialize views
        rvStaff = findViewById(R.id.rvStaff)
        btnBack = findViewById(R.id.btnBack)
        progressBar = findViewById(R.id.progressBar)

        // Setup RecyclerView
        rvStaff.layoutManager = LinearLayoutManager(this)

        apiService = RetrofitFactory.getInstance().create(HpApiService::class.java)

        btnBack.setOnClickListener {
            finish()
        }

        // Load staff automatically
        loadStaff()
    }

    private fun loadStaff() {
        progressBar.visibility = View.VISIBLE
        rvStaff.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val staff = withContext(Dispatchers.IO) {
                    apiService.getStaff()
                }

                if (staff.isNotEmpty()) {
                    val adapter = CharacterAdapter(staff)
                    rvStaff.adapter = adapter
                    rvStaff.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        this@StaffActivity,
                        getString(R.string.no_data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
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
