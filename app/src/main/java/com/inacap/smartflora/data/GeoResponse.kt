package com.inacap.smartflora.data

data class GeoResponseItem(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String?
)
typealias GeoResponse = List<GeoResponseItem>
