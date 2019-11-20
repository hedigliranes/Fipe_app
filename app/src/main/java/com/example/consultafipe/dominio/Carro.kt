package com.example.consultafipe.dominio

data class Carro (
    var id: Long,
    var Valor: String,
    var Marca: String,
    var Modelo: String,
    var AnoModelo: Int,
    var Combustivel: String,
    var CodigoFipe: String,
    var MesReferencia: String,
    var TipoVeiculo: Int,
    var SiglaCombustivel: String,
    var NomeTipo: String,
    var CodigoMarca: String,
    var CodigoModelo: Int,
    var CodigoAno: String
) {
    override fun toString(): String = Valor
}