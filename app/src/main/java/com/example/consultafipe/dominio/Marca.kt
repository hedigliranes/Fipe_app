package com.example.consultafipe.dominio

import android.view.ViewDebug

data class Marca (
    var nome: String,
    var codigo: String
) {
    override fun toString(): String = nome
}