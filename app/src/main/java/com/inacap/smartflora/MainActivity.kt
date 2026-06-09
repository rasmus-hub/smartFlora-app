package com.inacap.smartflora

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.inacap.smartflora.data.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val apiKey = "45e06a1c0c5625120bbc484fa3b09605"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDispositivo = findViewById<Button>(R.id.btnDispositivo)
        val barDevicesIcon = findViewById<ImageView>(R.id.barDevicesIcon)
        val barFarmingIcon = findViewById<ImageView>(R.id.barFarmingIcon)
        val barSettingsIcon = findViewById<ImageView>(R.id.barSettingsIcon)

        btnDispositivo.setOnClickListener {
            val intent = Intent(this, DispositivosActivity::class.java)
            startActivity(intent)
        }

        barDevicesIcon.setOnClickListener {
            val intent = Intent(this, DispositivosActivity::class.java)
            startActivity(intent)
        }

        barFarmingIcon.setOnClickListener {
        }

        barSettingsIcon.setOnClickListener {
        }

        obtenerClimaPorCiudad("Puerto Montt,CL")
    }

    private fun obtenerClimaPorCiudad(ciudad: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val geoResponse = RetrofitInstance.api.getCityCoordinates(ciudad, 1, apiKey)
                if (geoResponse.isSuccessful) {
                    val ciudadInfo = geoResponse.body()?.firstOrNull()
                    if (ciudadInfo != null) {
                        val lat = ciudadInfo.lat
                        val lon = ciudadInfo.lon

                        val weatherResponse = RetrofitInstance.api.getWeatherByCoordinates(lat, lon, apiKey)
                        if (weatherResponse.isSuccessful) {
                            val clima = weatherResponse.body()
                            withContext(Dispatchers.Main) {
                                Log.d("CLIMA", "Ciudad: ${clima?.name}")
                                Log.d("CLIMA", "Temperatura: ${clima?.main?.temp?.minus(273.15)
                                    ?.toInt()}°C")
                                Log.d("CLIMA", "Temperatura Minima: ${clima?.main?.temp_min?.minus(273.15)
                                    ?.toInt()}°C")
                                Log.d("CLIMA", "Temperatura Maxima: ${clima?.main?.temp_max?.minus(273.15)
                                    ?.toInt()}°C")
                                Log.d("CLIMA", "Humedad: ${clima?.main?.humidity}%")
                                Log.d("CLIMA", "Presión: ${clima?.main?.pressure}hPa")
                                Log.d("CLIMA", "Viento: ${clima?.wind?.speed} m/s")
                                Log.d("CLIMA", "Estado: ${clima?.weather?.firstOrNull()?.description}")

                                // Obtener elementos
                                val actualTemp = findViewById<TextView>(R.id.actualTemp)
                                val actualMaxTemp = findViewById<TextView>(R.id.actualMaxTemp)
                                val actualMinTemp = findViewById<TextView>(R.id.actualMinTemp)
                                val actualHumidity = findViewById<TextView>(R.id.actualHumidity)
                                val actualPh = findViewById<TextView>(R.id.actualPh)
                                val actualEC = findViewById<TextView>(R.id.actualEC)
                                val actualNPK = findViewById<TextView>(R.id.actualNPK)

                                // Actualizar variables
                                clima?.main?.temp?.minus(273.15)?.toInt()
                                    ?.let { actualTemp.setText("+${it}°C") }
                                clima?.main?.temp_min?.minus(273.15)?.toInt()
                                    ?.let { actualMinTemp.setText("MAX: ${it - 2}°C") }
                                clima?.main?.temp_max?.minus(273.15)?.toInt()
                                    ?.let { actualMaxTemp.setText("MIN: ${it + 2}°C") }
                                clima?.main?.humidity?.let { actualHumidity.setText("${it}%") }
                                actualPh.setText("7")
                                actualEC.setText("1.4 mS/cm")
                                actualNPK.setText("15-5-30")
                            }
                        } else {
                            Log.e("CLIMA", "Error clima: ${weatherResponse.errorBody()?.string()}")
                        }
                    } else {
                        Log.e("CLIMA", "No se encontró la ciudad")
                    }
                } else {
                    Log.e("CLIMA", "Error geolocalización: ${geoResponse.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("CLIMA", "Excepción: ${e.message}")
            }
        }
    }
}
