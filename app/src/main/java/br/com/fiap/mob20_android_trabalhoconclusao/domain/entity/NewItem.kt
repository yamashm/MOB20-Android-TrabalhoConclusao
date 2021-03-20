package br.com.fiap.mob20_android_trabalhoconclusao.domain.entity

data class NewItem(
        val name: String = "",
        val location: String = "",
        val phone: String = "",
        val description: String = "",
        val zipCode: String,
        var userId: String = ""
)