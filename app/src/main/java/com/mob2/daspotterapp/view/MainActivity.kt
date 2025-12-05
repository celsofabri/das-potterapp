package com.mob2.daspotterapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mob2.daspotterapp.R

class MainActivity : AppCompatActivity() {
    
    private lateinit var btnFindCharacter: Button
    private lateinit var btnListStaff: Button
    private lateinit var btnListStudents: Button
    private lateinit var btnExit: Button
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize views
        btnFindCharacter = findViewById(R.id.btnFindCharacter)
        btnListStaff = findViewById(R.id.btnListStaff)
        btnListStudents = findViewById(R.id.btnListStudents)
        btnExit = findViewById(R.id.btnExit)
        
        // Set click listeners
        btnFindCharacter.setOnClickListener {
            startActivity(Intent(this, FindCharacterActivity::class.java))
        }
        
        btnListStaff.setOnClickListener {
            startActivity(Intent(this, StaffActivity::class.java))
        }
        
        btnListStudents.setOnClickListener {
            startActivity(Intent(this, StudentsByHouseActivity::class.java))
        }
        
        btnExit.setOnClickListener {
            finishAffinity()
        }
    }
}
