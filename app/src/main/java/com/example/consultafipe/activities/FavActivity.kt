package com.example.consultafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consultafipe.R
import com.example.consultafipe.adapter.VeiculoAdapter
import com.example.consultafipe.dominio.Carro
import com.example.consultafipe.repositorio.SQLiteRepository
import com.example.consultafipe.repositorio.VeiculoRepository
import kotlinx.android.synthetic.main.activity_fav.*

class FavActivity : AppCompatActivity() {

    lateinit var veiculoAdapter: VeiculoAdapter
    lateinit var veiculoRepository: VeiculoRepository
    private var veiculos = mutableListOf<Carro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)

        veiculoRepository = SQLiteRepository(this)
        veiculoAdapter = VeiculoAdapter(veiculos)
        updateList()
        initRecyclerView()
    }
    private fun updateList(){
        veiculoRepository.list {
            veiculos.clear()
            veiculos.addAll(it)
        }
    }
    fun initRecyclerView(){

        rvVeiculos.adapter = veiculoAdapter

        val layoutMAnager = LinearLayoutManager(this)

        rvVeiculos.layoutManager = layoutMAnager

    }
}
