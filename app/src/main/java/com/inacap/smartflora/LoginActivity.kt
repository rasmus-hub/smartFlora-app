package com.inacap.smartflora

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Edge-to-edge config
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa ViewModel
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val btnTvLogin = findViewById<TextView>(R.id.tvLogin)

        btnIngresar.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()

            // Validaciones locales
            if (email.isEmpty()) {
                etEmail.error = "Ingresa un email válido"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "La contraseña no puede estar vacía"
                return@setOnClickListener
            }

            // Llamada al backend
            authViewModel.loginUser(email, password) { success, message ->
                runOnUiThread {
                    if (success) {
                        Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()  // Cierra LoginActivity para evitar volver atrás
                    } else {
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        btnTvLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}