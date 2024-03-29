package com.example.consultafipe.repositorio

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.example.consultafipe.dominio.Carro

class SQLiteRepository(ctx: Context):VeiculoRepository {

    private  val helper: VeiculoSqlHelper = VeiculoSqlHelper(ctx)

    private fun insert(veiculo: Carro){

        val db = helper.writableDatabase

        val cv = ContentValues().apply {
            put(COLUMN_VALOR,veiculo.Valor)
            put(COLUMN_VALORANTIGO,veiculo.Valor)
            put(COLUMN_ANOMODELO, veiculo.AnoModelo)
            put(COLUMN_CODIGOFIPE, veiculo.CodigoFipe)
            put(COLUMN_COMBUSTIVEL,veiculo.Combustivel)
            put(COLUMN_MARCA,veiculo.Marca)
            put(COLUMN_MESREFERENCIA,veiculo.MesReferencia)
            put(COLUMN_MODELO,veiculo.Modelo)
            put(COLUMN_SIGLACOMBUSTIVEL,veiculo.SiglaCombustivel)
            put(COLUMN_TIPOVEICULO,veiculo.TipoVeiculo)
            put(COLUMN_CODIGOMARCA,veiculo.CodigoMarca)
            put(COLUMN_NOMETIPO,veiculo.NomeTipo)
            put(COLUMN_CODIGOMODELO,veiculo.CodigoModelo)
            put(COLUMN_CODIGOANO,veiculo.CodigoAno)
        }

        val id = db.insert(TABLE_NAME, null, cv)
        if (id != -1L){
           veiculo.id = id
        }
        db.close()
    }

    private fun update(veiculo:Carro){
        val db = helper.writableDatabase

        val cv = ContentValues().apply {
            put(COLUMN_VALOR,veiculo.Valor)
            put(COLUMN_VALORANTIGO,veiculo.ValorAntigo)
            put(COLUMN_ANOMODELO, veiculo.AnoModelo)
            put(COLUMN_CODIGOFIPE, veiculo.CodigoFipe)
            put(COLUMN_COMBUSTIVEL,veiculo.Combustivel)
            put(COLUMN_MARCA,veiculo.Marca)
            put(COLUMN_MESREFERENCIA,veiculo.MesReferencia)
            put(COLUMN_MODELO,veiculo.Modelo)
            put(COLUMN_SIGLACOMBUSTIVEL,veiculo.SiglaCombustivel)
            put(COLUMN_TIPOVEICULO,veiculo.TipoVeiculo)
            put(COLUMN_CODIGOMARCA,veiculo.CodigoMarca)
            put(COLUMN_NOMETIPO,veiculo.NomeTipo)
            put(COLUMN_CODIGOMODELO,veiculo.CodigoModelo)
            put(COLUMN_CODIGOANO,veiculo.CodigoAno)
        }

        db.update(
            TABLE_NAME,
            cv,
            "$COLUMN_ID = ? ",
            arrayOf(veiculo.id.toString())
        )
        db.close()
    }


    override fun save(veiculo:Carro) {
        if (veiculo.id == 0L){
            veiculo.ValorAntigo = veiculo.Valor
            insert(veiculo)
        }else{
            update(veiculo)
        }
    }


    override fun remove(veiculo:Carro) {
        val db = helper.writableDatabase

        db.delete(
            TABLE_NAME,
            "$COLUMN_ID = ? ",
            arrayOf(veiculo.id.toString())
        )

        db.close()
    }


    override fun list(callback: (MutableList<Carro>)->Unit){
        var sql = "SELECT * FROM $TABLE_NAME"
        val args: Array<String>? = null
        sql += " ORDER BY $COLUMN_ID"
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        val veiculos = ArrayList<Carro>()
        while (cursor.moveToNext()){
            val veiculo = veiculoFromCursor(cursor)
            veiculos.add(veiculo)
        }
        cursor.close()
        db.close()

        callback(veiculos)
    }
    override fun getList(id:String): Carro?{
        var veiculo: Carro? = null

        var sql = "SELECT * FROM $TABLE_NAME WHERE ${COLUMN_CODIGOFIPE} = ?"
        val args: Array<String>? = arrayOf(id)
        val db = helper.readableDatabase
        val cursor = db.rawQuery(sql, args)
        while(cursor.moveToNext()){
            veiculo = veiculoFromCursor(cursor)
        }
        cursor.close()
        db.close()
        return veiculo
    }

    private fun veiculoFromCursor(cursor: Cursor): Carro {
        val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
        val Valor = cursor.getString(cursor.getColumnIndex(COLUMN_VALOR))
        val ValorAntigo = cursor.getString(cursor.getColumnIndex(COLUMN_VALORANTIGO))
        val Marca = cursor.getString(cursor.getColumnIndex(COLUMN_MARCA))
        val Modelo = cursor.getString(cursor.getColumnIndex(COLUMN_MODELO))
        val AnoModelo = cursor.getInt(cursor.getColumnIndex(COLUMN_ANOMODELO))
        val Combustivel = cursor.getString(cursor.getColumnIndex(COLUMN_COMBUSTIVEL))
        val CodigoFipe = cursor.getString(cursor.getColumnIndex(COLUMN_CODIGOFIPE))
        val MesReferencia = cursor.getString(cursor.getColumnIndex(COLUMN_MESREFERENCIA))
        val TipoVeiculo = cursor.getInt(cursor.getColumnIndex(COLUMN_TIPOVEICULO))
        val SiglaCombustivel = cursor.getString(cursor.getColumnIndex(COLUMN_SIGLACOMBUSTIVEL))
        val NomeTipo = cursor.getString(cursor.getColumnIndex(COLUMN_NOMETIPO))
        val CodigoMarca = cursor.getString(cursor.getColumnIndex(COLUMN_CODIGOMARCA))
        val CodigoModelo = cursor.getInt(cursor.getColumnIndex(COLUMN_CODIGOMODELO))
        val CodigoAno = cursor.getString(cursor.getColumnIndex(COLUMN_CODIGOANO))
        return Carro(id, Valor, ValorAntigo, Marca, Modelo, AnoModelo, Combustivel, CodigoFipe, MesReferencia, TipoVeiculo, SiglaCombustivel,NomeTipo,
            CodigoMarca,CodigoModelo,CodigoAno)
    }

}