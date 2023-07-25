package com.example.incidentrespondsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.button4.setOnClickListener {
            val incidentIntent = Intent(this, IncidentActivity::class.java)
            startActivity(incidentIntent)
        }


        binding.button5.setOnClickListener {
            val vulnerabilitytIntent = Intent(this,VulnerabilityReportActivty::class.java)
            startActivity(vulnerabilitytIntent)
        }



    }
}
