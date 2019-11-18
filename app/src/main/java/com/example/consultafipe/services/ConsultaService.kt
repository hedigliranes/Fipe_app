package com.example.consultafipe.services

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class ConsultaService: JobService()   {

    private val TAG = "ConsultaJobService"
    private var jobCancelled = false

    override fun onStartJob(params: JobParameters): Boolean {
        Log.d(TAG, "Job starteded")
        doBackgroundWork(params)

        return true
    }

    private fun doBackgroundWork(params: JobParameters) {
        Thread(Runnable {

            Log.d(TAG, "run: teste")


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