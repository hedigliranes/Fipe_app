package com.example.consultafipe.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consultafipe.R
import com.example.consultafipe.adapter.VeiculoAdapter
import com.example.consultafipe.dominio.Carro
import com.example.consultafipe.repositorio.SQLiteRepository

import kotlinx.android.synthetic.main.activity_fav.*

class FavActivity : AppCompatActivity() {

    private var veiculos = mutableListOf<Carro>()

    var adapter: VeiculoAdapter? = null

    private var veiculosRepository: SQLiteRepository? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fav)

        veiculosRepository = SQLiteRepository(this)

        updateList()

        initRecyclerView()
    }

    private fun list(veiculos:MutableList<Carro>){
        this.veiculos  = veiculos
        //Precisa reavaliar esse código para não criar sempre uma instância do adapter
        adapter = VeiculoAdapter(
            this.veiculos,
            this::onVeiculoItemClick,
            this::onVeiculoItemLongClick
        )
        rvVeiculos.adapter = adapter

    }

    private fun updateList(){
        veiculosRepository?.list { list(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode == 0 && resultCode == Activity.RESULT_OK){

            updateList()
            adapter?.notifyItemInserted(veiculos.lastIndex)

        }

        if (requestCode == 1 && resultCode == Activity.RESULT_OK){

            updateList()
            adapter?.notifyDataSetChanged()
        }

    }

    fun onVeiculoItemClick(veiculo: Carro, posicao: Int){

        var myIntent = Intent(this, VeiculoFavActivity::class.java)
        myIntent.putExtra("modelo",veiculo.Modelo)
        myIntent.putExtra("ano",veiculo.AnoModelo)
        myIntent.putExtra("valor",veiculo.Valor)
        myIntent.putExtra("marca",veiculo.Marca)
        myIntent.putExtra("combustivel",veiculo.Combustivel)
        myIntent.putExtra("mes",veiculo.MesReferencia)
        myIntent.putExtra("novo",veiculo.Valor)
        myIntent.putExtra("antigo",veiculo.ValorAntigo)
        myIntent.putExtra("requestCode", 10)

        startActivityForResult(myIntent,10)

    }

    fun onVeiculoItemLongClick(veiculo: Carro, posicao: Int):Boolean{
        veiculosRepository?.remove(veiculo)

        updateList()

        adapter?.notifyItemRemoved(posicao)

        return true
    }

    fun initRecyclerView(){

        rvVeiculos.adapter = adapter

        val layoutMAnager = LinearLayoutManager(this)

        rvVeiculos.layoutManager = layoutMAnager

    }
}
