package com.equipo6.crud_firebase

data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var password: String = ""
) {
    fun toMap(): Map<String, String>{
        return mapOf(
            "name" to name,
            "email" to email,
            "password" to password
        )
    }
}

