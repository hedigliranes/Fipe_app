package com.example.consultafipe.services

import com.example.consultafipe.dominio.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CarroService {

    @GET("{nome_tipo}/marcas")
    fun listarMarcas(
        @Path("nome_tipo") nomeTipo: String
    ): Call<List<Marca>>
    @GET("{nome_tipo}/marcas/{codigo_marca}/modelos")
    fun listarModelos(
        @Path("nome_tipo") nomeTipo: String,
        @Path("codigo_marca") codigoMarca: String): Call<ModelosResposta>
    @GET("{nome_tipo}/marcas/{codigo_marca}/modelos/{codigo_modelo}/anos")
    fun listarAnos(
        @Path("nome_tipo") nomeTipo: String,
        @Path("codigo_marca") codigoMarca: String,
        @Path("codigo_modelo") codigoModelo: Int): Call<List<ModeloAno>>
    @GET("{nome_tipo}/marcas/{codigo_marca}/modelos/{codigo_modelo}/anos/{codigo_ano}")
    fun obterCarro(
        @Path("nome_tipo") nomeTipo: String,
        @Path("codigo_marca") codigoMarca: String,
        @Path("codigo_modelo") codigoModelo: Int,
        @Path("codigo_ano") codigoAno: String): Call<Carro>
}