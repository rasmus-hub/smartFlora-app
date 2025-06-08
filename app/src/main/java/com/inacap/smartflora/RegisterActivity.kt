package com.inacap.smartflora

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var tvLogin: TextView


    private lateinit var borderNormal: Drawable
    private lateinit var borderError: Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsuario = findViewById(R.id.etUsuario)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        tvLogin = findViewById(R.id.tvLogin)

        borderNormal = ContextCompat.getDrawable(this, R.drawable.edittext_normal_border)!!
        borderError = ContextCompat.getDrawable(this, R.drawable.edittext_error_border)!!

        // Iniciar botón deshabilitado
        btnRegistrar.isEnabled = false

        // Añadimos TextWatchers para validar en tiempo real
        val watcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                validarCampos()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        etUsuario.addTextChangedListener(watcher)
        etEmail.addTextChangedListener(watcher)
        etPassword.addTextChangedListener(watcher)
        etConfirmPassword.addTextChangedListener(watcher)

        btnRegistrar.setOnClickListener {
            // Aquí ya están validados, podemos proceder
            irAlLogin()
        }

        tvLogin.setOnClickListener {
            irAlLogin()
        }
    }

    private fun validarCampos() {
        var valido = true

        // Validar usuario (mín 4 caracteres)
        if (etUsuario.text.toString().length >= 4) {
            etUsuario.background = borderNormal
            etUsuario.error = null
        } else {
            etUsuario.background = borderError
            etUsuario.error = "Mínimo 4 caracteres"
            valido = false
        }

        // Validar email (expresión simple)
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.background = borderNormal
            etEmail.error = null
        } else {
            etEmail.background = borderError
            etEmail.error = "Email inválido"
            valido = false
        }

        // Validar contraseña (mín 6 caracteres y al menos un número)
        val pass = etPassword.text.toString()
        if (pass.length >= 6 && pass.any { it.isDigit() }) {
            etPassword.background = borderNormal
            etPassword.error = null
        } else {
            etPassword.background = borderError
            etPassword.error = "6+ caracteres y al menos un número"
            valido = false
        }

        // Validar confirmación de contraseña
        if (etConfirmPassword.text.toString() == pass && etConfirmPassword.text.toString().isNotEmpty()) {
            etConfirmPassword.background = borderNormal
            etConfirmPassword.error = null
        } else {
            etConfirmPassword.background = borderError
            etConfirmPassword.error = "Las contraseñas no coinciden"
            valido = false
        }

        btnRegistrar.isEnabled = valido
    }

    private fun irAlLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
