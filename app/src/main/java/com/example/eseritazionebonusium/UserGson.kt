package com.example.eseritazionebonusium

data class UserGson(
    var username: String = "",
    var password: String = "",
    var city: String = "",
    var birth: String = "",
    var eliminare: Boolean = false,
    var admin: Int = 0
)
