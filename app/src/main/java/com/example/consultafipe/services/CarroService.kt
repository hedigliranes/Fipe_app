package com.example.consultafipe.services

import com.example.consultafipe.dominio.Carro
import com.example.consultafipe.dominio.Marca
import com.example.consultafipe.dominio.Modelo
import com.example.consultafipe.dominio.ModeloAno
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CarroService {

    @GET("carros/marcas")
    fun listarMarcas(): Call<List<Marca>>
    @GET("carros/marcas/{codigo}/modelos")
    fun listarModelos(@Path("codigo") codigo: Int): Call<List<Modelo>>
    @GET("carros/marcas/{codigo_marca}/modelos/{codigo_modelo}/anos")
    fun listarAnos(
        @Path("codigo_marca") codigoMarca: Int,
        @Path("codigo_modelo") codigoModelo: Int): Call<List<ModeloAno>>
    @GET("carros/marcas/{codigo_marca}/modelos/{codigo_modelo}/anos/{codigo_ano}")
    fun obterCarro(
        @Path("codigo_marca") codigoMarca: Int,
        @Path("codigo_modelo") codigoModelo: Int,
        @Path("codigo_ano") codigoAno: Int): Call<Carro>
}