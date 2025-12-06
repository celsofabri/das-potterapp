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
import com.mob2.daspotterapp.util.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StaffActivity : AppCompatActivity() {

    private lateinit var tvStaffList: TextView
    private lateinit var btnBack: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var apiService: HpApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_staff)

        // Initialize views
        tvStaffList = findViewById(R.id.tvStaffList)
        btnBack = findViewById(R.id.btnBack)
        progressBar = findViewById(R.id.progressBar)

        apiService = RetrofitFactory.getInstance().create(HpApiService::class.java)

        // Fetch staff on activity creation
        fetchStaff()

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchStaff() {
        progressBar.visibility = View.VISIBLE
        tvStaffList.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val staff = withContext(Dispatchers.IO) {
                    apiService.getStaff()
                }

                if (staff.isNotEmpty()) {
                    // Exibir apenas os nomes conforme especificação
                    val staffNames = staff.joinToString("\n") { it.name }
                    tvStaffList.text = staffNames
                    tvStaffList.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        this@StaffActivity,
                        getString(R.string.no_staff_found),
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
