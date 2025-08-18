package com.inacap.smartflora.models

// ------------------- LOGIN -------------------
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val user_id: String?
)

// ------------------- REGISTRO -------------------
data class RegisterRequest(
    val email: String,
    val password: String
)

data class RegisterResponse(
    val message: String,
    val id: String?
)

// ------------------- FORGOT PASSWORD -------------------
data class ForgotPasswordRequest(
    val email: String
)

data class ForgotPasswordResponse(
    val message: String,
    val token: String?
)

// ------------------- RESET PASSWORD -------------------
data class ResetPasswordRequest(
    val token: String,
    val new_password: String
)

data class ResetPasswordResponse(
    val message: String
)

// ------------------- DELETE ACCOUNT -------------------
data class DeleteAccountRequest(
    val email: String,
    val password: String
)

data class DeleteAccountResponse(
    val message: String
)
