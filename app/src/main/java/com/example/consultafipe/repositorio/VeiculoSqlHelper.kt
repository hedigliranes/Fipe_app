package com.example.consultafipe.repositorio

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class VeiculoSqlHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){
    override fun onCreate(p0: SQLiteDatabase?) {
        p0!!.execSQL(
            "CREATE TABLE $TABLE_NAME("+
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "$COLUMN_VALOR TEXT NOT NULL, "+
                    "$COLUMN_VALORANTIGO TEXT, "+
                    "$COLUMN_MARCA TEXT NOT NULL, "+
                    "$COLUMN_MODELO TEXT NOT NULL, "+
                    "$COLUMN_ANOMODELO TEXT NOT NULL, "+
                    "$COLUMN_COMBUSTIVEL TEXT NOT NULL, "+
                    "$COLUMN_CODIGOFIPE TEXT NOT NULL, "+
                    "$COLUMN_MESREFERENCIA TEXT NOT NULL, "+
                    "$COLUMN_TIPOVEICULO TEXT NOT NULL, "+
                    "$COLUMN_NOMETIPO TEXT NOT NULL, "+
                    "$COLUMN_CODIGOMARCA TEXT NOT NULL, "+
                    "$COLUMN_CODIGOMODELO TEXT NOT NULL, "+
                    "$COLUMN_CODIGOANO TEXT NOT NULL, "+
                    "$COLUMN_SIGLACOMBUSTIVEL TEXT NOT NULL)")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}