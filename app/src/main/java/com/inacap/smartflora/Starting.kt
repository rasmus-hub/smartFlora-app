package com.inacap.smartflora

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Starting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starting)


        val logoImageView = findViewById<ImageView>(R.id.ivLogo)

        val animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)

        logoImageView.startAnimation(animation)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}
