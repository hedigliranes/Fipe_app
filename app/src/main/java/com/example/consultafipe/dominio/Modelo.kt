package com.example.consultafipe.dominio

data class Modelo(
    var nome: String,
    var codigo: String
) {
    override fun toString(): String = nome
}