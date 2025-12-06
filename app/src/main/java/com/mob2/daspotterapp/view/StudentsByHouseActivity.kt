package com.mob2.daspotterapp.view

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
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

class StudentsByHouseActivity : AppCompatActivity() {

    private lateinit var radioGroupHouses: RadioGroup
    private lateinit var btnListStudents: Button
    private lateinit var btnBack: Button
    private lateinit var tvResult: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var apiService: HpApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_by_house)

        // Initialize views
        radioGroupHouses = findViewById(R.id.radioGroupHouses)
        btnListStudents = findViewById(R.id.btnListStudents)
        btnBack = findViewById(R.id.btnBack)
        tvResult = findViewById(R.id.tvResult)
        progressBar = findViewById(R.id.progressBar)

        apiService = RetrofitFactory.getInstance().create(HpApiService::class.java)

        btnListStudents.setOnClickListener {
            val selectedId = radioGroupHouses.checkedRadioButtonId

            if (selectedId == -1) {
                Toast.makeText(this, R.string.error_no_house_selected, Toast.LENGTH_SHORT).show()
            } else {
                val selectedRadioButton = findViewById<RadioButton>(selectedId)
                val houseName = when (selectedRadioButton.id) {
                    R.id.rbGryffindor -> "gryffindor"
                    R.id.rbSlytherin -> "slytherin"
                    R.id.rbHufflepuff -> "hufflepuff"
                    R.id.rbRavenclaw -> "ravenclaw"
                    else -> ""
                }

                if (houseName.isNotEmpty()) {
                    fetchStudents(houseName)
                }
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    private fun fetchStudents(house: String) {
        progressBar.visibility = View.VISIBLE
        tvResult.text = getString(R.string.loading)

        lifecycleScope.launch {
            try {
                val students = withContext(Dispatchers.IO) {
                    apiService.getStudentsByHouse(house)
                }

                if (students.isNotEmpty()) {
                    val studentNames = students.joinToString(separator = "\n") { "• ${it.name}" }
                    tvResult.text = studentNames
                } else {
                    tvResult.text = "Nenhum estudante encontrado para esta casa."
                }
            } catch (e: Exception) {
                tvResult.text = getString(R.string.error_network)
                Toast.makeText(
                    this@StudentsByHouseActivity,
                    getString(R.string.error_network),
                    Toast.LENGTH_SHORT
                ).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
