package com.example.consultafipe.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.util.Log
import android.view.View
import com.example.consultafipe.dominio.Carro
import com.example.consultafipe.repositorio.SQLiteRepository
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.consultafipe.adapter.VeiculoAdapter
import com.example.consultafipe.repositorio.VeiculoSqlHelper
import kotlinx.android.synthetic.main.activity_fav.*


class ConsultaService: JobService()   {

    private val TAG = "ConsultaJobService"
    private var jobCancelled = false
    private var veiculosRepository: SQLiteRepository? = null
    private var veiculos = mutableListOf<Carro>()


    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job started")

        veiculosRepository = SQLiteRepository(this)


        veiculosRepository?.list{list(it)}

        doBackgroundWork(params)

        return true
    }

    private fun list(veiculos:MutableList<Carro>){

        this.veiculos  = veiculos

    }

    private fun doBackgroundWork(params: JobParameters) {
        veiculosRepository = SQLiteRepository(this)

        Thread(Runnable {

            for (veiculo in this.veiculos){

                val call = RetrofitInitializer().carroService().obterCarro(veiculo.NomeTipo, veiculo.CodigoMarca,
                    veiculo.CodigoModelo, veiculo.CodigoAno)
                call.enqueue(object : Callback<Carro> {
                    override fun onResponse(call: Call<Carro>?, response: Response<Carro>?) {
                        response?.body()?.let {
                            Log.d("Text",it.Valor)
                            veiculo.Valor = it.Valor
                            veiculosRepository?.save(veiculo)

                        }
                    }
                    override fun onFailure(call: Call<Carro>, t: Throwable) {
                        Log.d("ERRO","ERRO")
                    }
                })

            }

            Log.d(TAG, "Job finished")
            jobFinished(params, false)
        }).start()
    }

    override
    fun onStopJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job cancelled before completion")
        jobCancelled = true
        return true
    }
}