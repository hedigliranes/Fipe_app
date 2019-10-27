package com.example.consultafipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var marca: String = adapter?.getItem(p2).toString()

        Toast.makeText(this,marca, Toast.LENGTH_LONG).show()    }

    private var adapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item)

        getMarcas()

        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = this

    }

    fun getMarcas() {

        val call = RetrofitInitializer().carroService().listarMarcas()

        buscarAssincrono(call)
    }

    fun preencherLista(marcas: List<Marca>?){
        for (marca in marcas!!){
            adapter?.add(marca?.nome.toString())
        }
    }

    private fun buscarAssincrono(call: Call<List<Marca>>) {

        call.enqueue(object : Callback<List<Marca>> {

            override fun onResponse(call: Call<List<Marca>?>?, response: Response<List<Marca>?>?) {
                response?.body()?.let {
                    var marcas: List<Marca>? = it
                    preencherLista(marcas)
                }
            }

            override fun onFailure(call: Call<List<Marca>>, t: Throwable) {

                exibirErro(t)
            }
        })
    }

    private fun exibirErro(t:Throwable){
        Toast.makeText(this, "CEP não encontrado", Toast.LENGTH_SHORT).show()
    }


}
