package com.inacap.smartflora

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.inacap.smartflora.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding
    private lateinit var adapter: IntroAdapter
    private var yaCambiado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val introList = listOf(
            IntroSlide(R.drawable.img1, "Gestiona tu invernadero de acuerdo a la información recolectada"),
            IntroSlide(R.drawable.img2, "Recibe notificaciones de riesgo sobre el cultivo"),
            IntroSlide(R.drawable.img3, "Gestiona el sistema con solo unos clics")
        )

        adapter = IntroAdapter(introList)
        binding.viewPager.adapter = adapterasesioaejsauihsauihsauiehsaui

        binding.btnSiguiente.setOnClickListener {
            if (binding.viewPager.currentItem < introList.lastIndex) {
                binding.viewPager.currentItem += 1
            } else {
                irARegistro()
            }
        }
    }

    private fun irARegistro() {
        if (!yaCambiado) yaCambiado = true
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }
}
