package com.example.consultafipe.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.consultafipe.R
import kotlinx.android.synthetic.main.activity_list_veiculo.*

class VeiculoFavActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_veiculo)

        veiculo_modelo.text = intent.getStringExtra("modelo")
        veiculo_marca.text = intent.getStringExtra("marca")
        veiculo_ano.text = intent.getIntExtra("ano",0).toString()
        veiculo_combustivel.text = intent.getStringExtra("combustivel")
        veiculo_mesReferencia.text = intent.getStringExtra("mes")
        veiculo_preco.text = intent.getStringExtra("valor")


    }
}