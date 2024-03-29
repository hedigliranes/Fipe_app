package com.example.consultafipe.repositorio

import com.example.consultafipe.dominio.Carro

interface VeiculoRepository {
    fun save(veiculo: Carro)
    fun remove(veiculo: Carro)
    fun list(callback:(MutableList<Carro>) -> Unit)
    fun getList(id:String): Carro?
}



