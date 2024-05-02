// MainActivity.kt
package com.example.ca2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar // Make sure to import the Toolbar class

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Assuming you have a Toolbar with the id "toolbar" in your layout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = "Kaushik Toor and Travels"

        val destinations = listOf("Manali", "Shimla", "New Delhi", "Muzaffarpur", "Patna","Dumari")

        for (destination in destinations) {
            val button = Button(this)
            button.text = destination
            button.setOnClickListener { onDestinationClick(destination) }
            findViewById<LinearLayout>(R.id.destinationsLayout).addView(button)
        }

        val jobSchedulerHelper = JobSchedulerHelper()

        // Call scheduleJob with a default destination or update based on your use case
        jobSchedulerHelper.scheduleJob(this, "DefaultDestination")
    }

    private fun onDestinationClick(destination: String) {
        val intent = Intent(this, DestinationActivity::class.java)
        intent.putExtra("destination", destination)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        val jobSchedulerHelper = JobSchedulerHelper()
        jobSchedulerHelper.cancelJob(this)
    }
}
