package com.example.consultafipe.dominio

data class Modelo(
    var nome: String,
    var codigo: Int
) {
    override fun toString(): String = nome
}