package com.example.consultafipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.consultafipe.R
import com.example.consultafipe.dominio.Carro
import kotlinx.android.synthetic.main.item_veiculo.view.*


class VeiculoAdapter (
    var veiculos: List<Carro>,
    private val callback: (Carro, Int) -> Unit,
    private val callbackLong:(Carro, Int)-> Boolean):
    RecyclerView.Adapter<VeiculoAdapter.VH>() {

    override fun getItemCount(): Int = veiculos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_veiculo, parent, false)

        val vh = VH(v)

        vh.itemView.setOnClickListener {
            val message = veiculos[vh.adapterPosition]
            callback(message, vh.adapterPosition)
        }

        vh.itemView.setOnLongClickListener {

            callbackLong(veiculos[vh.adapterPosition], vh.adapterPosition)
        }
        return vh
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val veiculo = veiculos[position]
        holder.txtVeiculo.text = veiculo.Modelo
        holder.txtValor.text = veiculo.Valor
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtVeiculo: TextView = itemView.txtVeiculo
        val txtValor: TextView = itemView.txtValor
    }
}