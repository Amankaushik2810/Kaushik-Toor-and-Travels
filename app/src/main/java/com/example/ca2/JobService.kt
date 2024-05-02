package com.example.ca2

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

class YourJobService : JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job started")
        // Implement your job logic here

        // If the job is short-lived, call jobFinished to indicate that the job is done
        jobFinished(params, false)
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d(TAG, "Job stopped")
        return false
    }

    companion object {
        private const val TAG = "YourJobService"
    }
}
