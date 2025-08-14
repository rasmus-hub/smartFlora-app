package com.inacap.smartflora

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {

    suspend fun loginUser(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.instance.login(User(email, password))
            }

            if (response.isSuccessful) {
                val token = response.body()?.token  // Asume que tu API devuelve un token
                // Guarda el token en SharedPreferences o similar
                onResult(true, "Bienvenido")
            } else {
                onResult(false, response.body()?.error ?: "Error desconocido")
            }
        } catch (e: Exception) {
            onResult(false, "Error de red: ${e.message}")
        }
    }
}