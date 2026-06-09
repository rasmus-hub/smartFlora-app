package com.inacap.smartflora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class DevicesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)

        val btnNavHome = findViewById<ImageView>(R.id.btnNavHome)
        val btnNavDevices = findViewById<ImageView>(R.id.btnNavDevices)
        val btnNavFarming = findViewById<ImageView>(R.id.btnNavFarming)
        val btnNavSettings = findViewById<ImageView>(R.id.btnNavSettings)

        btnNavHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnNavDevices.setOnClickListener {
            Toast.makeText(this, "Ya estás en Dispositivos", Toast.LENGTH_SHORT).show()
        }

        btnNavFarming.setOnClickListener {
            val intent = Intent(this, FarmingActivity::class.java)
            startActivity(intent)
        }

        btnNavSettings.setOnClickListener {
        }
    }
}