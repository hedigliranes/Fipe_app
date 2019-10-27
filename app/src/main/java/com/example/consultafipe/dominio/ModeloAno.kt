package com.example.consultafipe.dominio

data class ModeloAno(
    var nome: String,
    var codigo: String
){
    override fun toString(): String = nome
}