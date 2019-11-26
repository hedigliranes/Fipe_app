package com.example.consultafipe.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.consultafipe.dominio.*
import com.example.consultafipe.services.RetrofitInitializer
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.consultafipe.repositorio.SQLiteRepository
import com.example.consultafipe.repositorio.VeiculoRepository

import android.app.job.JobScheduler
import android.app.job.JobInfo
import android.content.ComponentName

import android.content.Context
import android.util.Log
import com.example.consultafipe.R
import com.example.consultafipe.notifications.PriceNotification
import com.example.consultafipe.services.ConsultaService
import kotlinx.android.synthetic.main.activity_list_veiculo.*


/* todo
* favoritar um veiculo
* lista de favoritos
* historico de preços
* broadcast para agendar a consulta diariamente
* */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var adapter: ArrayAdapter<Marca>
    private lateinit var adapterModelo: ArrayAdapter<Modelo>
    private lateinit var adapterAno: ArrayAdapter<ModeloAno>
    private lateinit var adapterTipo: ArrayAdapter<String>
    private var veiculo: Carro? = null
    private lateinit var repository: VeiculoRepository;
    private val TAG = "MainActivity"


    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        progress.visibility = View.VISIBLE
        btAddFav.visibility = View.GONE
        valorVeiculo.visibility = View.GONE
        labelValor.visibility = View.INVISIBLE
        btDet.visibility = View.INVISIBLE

        when(p0?.adapter){
            adapter -> this.carregarModelos()
            adapterModelo -> this.carregarAnos()
            adapterAno -> this.carregarCarro()
            adapterTipo -> this.carregarMarcas()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapterTipo = ArrayAdapter(this,android.R.layout.simple_spinner_item, arrayOf("carros", "motos", "caminhoes"))
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerTipo.adapter = adapterTipo
        spinnerTipo.onItemSelectedListener = this
        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        adapterModelo = ArrayAdapter(this,android.R.layout.simple_spinner_item)
        adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerModelo.adapter = adapterModelo
        spinnerModelo.onItemSelectedListener = this
        adapterAno = ArrayAdapter(this,android.R.layout.simple_spinner_item)
        adapterAno.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerAno.adapter = adapterAno
        spinnerAno.onItemSelectedListener = this
        progress.visibility = View.VISIBLE
        this.scheduleJob()
        repository = SQLiteRepository(this)
        this.carregarMarcas()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.menuFav){
            var myIntent = Intent(this, FavActivity::class.java)
            startActivity(myIntent)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
    private fun carregarMarcas(){
        val positionTipo = spinnerTipo.selectedItemPosition
        val nomeTipo = adapterTipo.getItem(positionTipo)!!
        adapter.clear()
        adapterModelo.clear()
        adapterAno.clear()
        val call = RetrofitInitializer().carroService().listarMarcas(nomeTipo)
        call.enqueue(object : Callback<List<Marca>> {
            override fun onResponse(call: Call<List<Marca>?>?, response: Response<List<Marca>?>?) {
                response?.body()?.let {
                    adapter.addAll(it)
                }
            }

            override fun onFailure(call: Call<List<Marca>>, t: Throwable) {
                exibirErro(t)
            }
        })
    }
    private fun carregarModelos(){
        val positionTipo = spinnerTipo.selectedItemPosition
        val nomeTipo = adapterTipo.getItem(positionTipo)!!
        adapterModelo.clear()
        adapterAno.clear()
        val positionMarca = spinner.selectedItemPosition
        val codigoMarca = adapter.getItem(positionMarca)!!.codigo
        val call = RetrofitInitializer().carroService().listarModelos(nomeTipo, codigoMarca)
        call.enqueue(object : Callback<ModelosResposta> {
            override fun onResponse(call: Call<ModelosResposta>?, response: Response<ModelosResposta>?) {
                response?.body()?.let {
                    adapterModelo.addAll(it.modelos)
                }
            }
            override fun onFailure(call: Call<ModelosResposta>, t: Throwable) {
                exibirErro(t)
            }
        })
    }
    private fun carregarAnos(){
        val positionTipo = spinnerTipo.selectedItemPosition
        val nomeTipo = adapterTipo.getItem(positionTipo)!!
        adapterAno.clear()
        val positionMarca = spinner.selectedItemPosition
        val codigoMarca = adapter.getItem(positionMarca)!!.codigo
        val positionModelo = spinnerModelo.selectedItemPosition
        val codigoModelo = adapterModelo.getItem(positionModelo)!!.codigo
        val call = RetrofitInitializer().carroService().listarAnos(nomeTipo, codigoMarca, codigoModelo)
        call.enqueue(object : Callback<List<ModeloAno>> {
            override fun onResponse(call: Call<List<ModeloAno>?>?, response: Response<List<ModeloAno>?>?) {
                response?.body()?.let {
                    adapterAno.addAll(it)
                }
            }
            override fun onFailure(call: Call<List<ModeloAno>>, t: Throwable) {
                exibirErro(t)
            }
        })
    }
    private fun carregarCarro(){
        val positionTipo = spinnerTipo.selectedItemPosition
        val nomeTipo = adapterTipo.getItem(positionTipo)!!
        val positionMarca = spinner.selectedItemPosition
        val codigoMarca = adapter.getItem(positionMarca)!!.codigo
        val positionModelo = spinnerModelo.selectedItemPosition
        val codigoModelo = adapterModelo.getItem(positionModelo)!!.codigo
        val positionAno = spinnerAno.selectedItemPosition
        val codigoAno = adapterAno.getItem(positionAno)!!.codigo
        val call = RetrofitInitializer().carroService().obterCarro(nomeTipo, codigoMarca, codigoModelo, codigoAno)
        call.enqueue(object : Callback<Carro> {
            override fun onResponse(call: Call<Carro>?, response: Response<Carro>?) {
                response?.body()?.let {
                    it.NomeTipo = nomeTipo
                    it.CodigoMarca = codigoMarca
                    it.CodigoAno = codigoAno
                    it.CodigoModelo = codigoModelo
                    veiculo = it
                    valorVeiculo.text = "Preço: ${it.Valor}"
                    progress.visibility = View.GONE
                    btAddFav.visibility = View.VISIBLE
                    valorVeiculo.visibility = View.VISIBLE
                    labelValor.visibility = View.VISIBLE
                    btDet.visibility = View.VISIBLE

                }
            }
            override fun onFailure(call: Call<Carro>, t: Throwable) {
                exibirErro(t)
            }
        })

        this.cancelJob()
    }

    private fun exibirErro(t:Throwable){
        Toast.makeText(this, t.message!!, Toast.LENGTH_LONG).show()
    }

    fun addFav(view: View) {

        if(veiculo != null) {
            var veiculoSalvo:Carro? = null

            veiculoSalvo = repository.getList(veiculo!!.CodigoFipe)
            if(veiculoSalvo != null){
                Toast.makeText(this, "Veiculo já está nos favoritos!", Toast.LENGTH_LONG).show()
                veiculo = veiculoSalvo
            } else {
                repository.save(veiculo as Carro)
            }
        }
    }
    fun scheduleJob() {
        val componentName = ComponentName(this, ConsultaService::class.java)
        val info = JobInfo.Builder(123, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
            .setPersisted(true)
//            .setMinimumLatency(1)
//            .setOverrideDeadline(1)
            .setPeriodic((30 * 24 * 60 * 1000).toLong())
            .build()

        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG  , "Job scheduled")
        } else {
            Log.d(TAG, "Job scheduling failed")
        }
    }

    fun cancelJob() {
        val scheduler = getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(123)
        Log.d(TAG, "Job cancelled")
    }

    fun verDet(view: View) {
        var myIntent = Intent(this, VeiculoFavActivity::class.java)
        myIntent.putExtra("modelo",veiculo!!.Modelo)
        myIntent.putExtra("ano",veiculo!!.AnoModelo)
        myIntent.putExtra("valor",veiculo!!.Valor)
        myIntent.putExtra("marca",veiculo!!.Marca)
        myIntent.putExtra("combustivel",veiculo!!.Combustivel)
        myIntent.putExtra("mes",veiculo!!.MesReferencia)

        startActivity(myIntent)
    }


}
