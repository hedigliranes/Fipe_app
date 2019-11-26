package com.example.consultafipe.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.consultafipe.R
import kotlinx.android.synthetic.main.activity_list_veiculo.*

class VeiculoFavActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_veiculo)

        var requestCode = intent.getIntExtra("requestCode",0)


        if(requestCode == 10) {
            var valor = intent.getStringExtra("novo").replace("R$ ", "").replace(".", "").replace(
                ",",
                ""
            ).toDouble() / 100
            var valorAntigo =
                intent.getStringExtra("antigo").replace("R$ ", "").replace(".", "").replace(
                    ",",
                    ""
                ).toDouble() / 100
            percent.text = String.format("%.2f", (((((valor * 100) / valorAntigo)) - 100))) + "%"
            variacao.text = "Variação de Preço"


        }

        veiculo_modelo.text = intent.getStringExtra("modelo")
        veiculo_marca.text = intent.getStringExtra("marca")
        veiculo_ano.text = intent.getIntExtra("ano",0).toString()
        veiculo_combustivel.text = intent.getStringExtra("combustivel")
        veiculo_mesReferencia.text = intent.getStringExtra("mes")
        veiculo_preco.text = intent.getStringExtra("valor")


    }
}