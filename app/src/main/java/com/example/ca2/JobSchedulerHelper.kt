// JobSchedulerHelper.kt
package com.example.ca2

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.os.Build
import android.os.PersistableBundle

class JobSchedulerHelper {

    fun scheduleJob(context: Context, destination: String) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler

        // Use PersistableBundle to pass data to the job
        val extras = PersistableBundle().apply {
            putString("destination", destination)
        }

        val componentName = ComponentName(context, YourJobService::class.java) // Replace with your actual JobService class

        val jobInfoBuilder = JobInfo.Builder(JOB_ID, componentName)
            .setRequiresCharging(false)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)

        // For API level 21 and higher, use setExtras
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            jobInfoBuilder.setExtras(extras)
        } else {
            jobInfoBuilder.setExtras(extras)
        }

        val jobInfo = jobInfoBuilder.build()
        jobScheduler.schedule(jobInfo)
    }

    fun cancelJob(context: Context) {
        val jobScheduler = context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(JOB_ID)
    }

    companion object {
        const val JOB_ID = 123 // Replace with your unique job ID
    }
}
