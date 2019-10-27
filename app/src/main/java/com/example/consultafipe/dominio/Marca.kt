package com.example.consultafipe.dominio

data class Marca (
    var nome: String,
    var codigo: String
) {
    override fun toString(): String = nome
}