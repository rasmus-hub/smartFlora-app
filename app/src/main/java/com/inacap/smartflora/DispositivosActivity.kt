package com.inacap.smartflora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast

class DispositivosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispositivos)
        val btnInicio = findViewById<ImageView>(R.id.btnInicio)
        val btnDispositivos = findViewById<ImageView>(R.id.btnDispositivo)

        btnInicio.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnDispositivos.setOnClickListener {

            Toast.makeText(this, "Ya estás en Dispositivos", Toast.LENGTH_SHORT).show()
        }
    }
}