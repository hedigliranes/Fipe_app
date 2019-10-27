package com.example.consultafipe.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {

    private val retrofit:Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://parallelum.com.br/fipe/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun carroService(): CarroService {
        return retrofit.create(CarroService::class.java)
    }

}