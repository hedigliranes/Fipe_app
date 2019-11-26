package com.example.consultafipe.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.example.consultafipe.dominio.Carro
import com.example.consultafipe.notifications.PriceNotification
import com.example.consultafipe.repositorio.SQLiteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



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

    private fun changeValue(){
        PriceNotification.notificationWithAction(this)
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
                            var valorInt = it.Valor.replace("R$ ","").replace(".","").replace(",","").toInt()
                            var valorNewInt = veiculo.Valor.replace("R$ ","").replace(".","").replace(",","").toInt()


                            Log.d("TESTE",valorInt.toString())
                            Log.d("TESTE",valorNewInt.toString())



                            if(valorInt < valorNewInt || valorInt > valorNewInt ) {
                                changeValue()
                            }

                            veiculo.ValorAntigo = veiculo.Valor
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