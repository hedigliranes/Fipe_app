package com.example.consultafipe.services

import com.example.consultafipe.dominio.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CarroService {

    @GET("carros/marcas")
    fun listarMarcas(): Call<List<Marca>>
    @GET("carros/marcas/{codigo_marca}/modelos")
    fun listarModelos(@Path("codigo_marca") codigoMarca: String): Call<ModelosResposta>
    @GET("carros/marcas/{codigo_marca}/modelos/{codigo_modelo}/anos")
    fun listarAnos(
        @Path("codigo_marca") codigoMarca: String,
        @Path("codigo_modelo") codigoModelo: Int): Call<List<ModeloAno>>
    @GET("carros/marcas/{codigo_marca}/modelos/{codigo_modelo}/anos/{codigo_ano}")
    fun obterCarro(
        @Path("codigo_marca") codigoMarca: String,
        @Path("codigo_modelo") codigoModelo: Int,
        @Path("codigo_ano") codigoAno: String): Call<Carro>
}