package com.example.consultafipe.services

import com.example.consultafipe.dominio.Marca
import retrofit2.Call
import retrofit2.http.GET

interface CarroService {

    @GET("carros/marcas")
    fun listarMarcas(): Call<List<Marca>>
}