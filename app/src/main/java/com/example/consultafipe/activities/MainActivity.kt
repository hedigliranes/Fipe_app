package com.example.consultafipe.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.consultafipe.R
import com.example.consultafipe.dominio.Marca
import com.example.consultafipe.services.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private var adapter: ArrayAdapter<Marca>? = null

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        val marca: String = adapter?.getItem(p2)!!.nome
        Toast.makeText(this, marca, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item)
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        this.carregarMarcas()


    }

    private fun carregarMarcas(){
        val call = RetrofitInitializer().carroService().listarMarcas()
        call.enqueue(object : Callback<List<Marca>> {
            override fun onResponse(call: Call<List<Marca>?>?, response: Response<List<Marca>?>?) {
                response?.body()?.let {
                    adapter?.addAll(it)
                }
            }

            override fun onFailure(call: Call<List<Marca>>, t: Throwable) {
                exibirErro(t)
            }
        })
    }

    private fun exibirErro(t:Throwable){
        Toast.makeText(this, "CEP não encontrado\n${t.message!!}", Toast.LENGTH_SHORT).show()
    }


}
