package com.example.ca2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class TripDetailsActivity : AppCompatActivity() {

    private lateinit var destination: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_details)

        destination = intent.getStringExtra("destination")?.trim() ?: ""

        val destinationTextView = findViewById<TextView>(R.id.destinationTextView)
        destinationTextView.text = "Details for $destination"

        val destinationImageView = findViewById<ImageView>(R.id.destinationImageView)
        // Set the image based on the destination
        setDestinationImage(destination, destinationImageView)

        val detailsTextView = findViewById<TextView>(R.id.detailsTextView)
        detailsTextView.text = getDetailsForDestination(destination)

        // Set onClickListener for the "Set Alarm" button
        val showAlarmButton = findViewById<Button>(R.id.showalarm)
        showAlarmButton.setOnClickListener {
            navigateToMainActivity2()
        }
    }

    private fun setDestinationImage(destination: String, imageView: ImageView) {
        // Set the image based on the destination
        when (destination.toLowerCase(Locale.ROOT)) {
            "manali" -> imageView.setImageResource(R.drawable.pic1)
            "shimla" -> imageView.setImageResource(R.drawable.pic2)
            // Add more cases for other destinations
            else -> imageView.setImageResource(R.drawable.pic5)
        }
    }

    private fun getDetailsForDestination(destination: String): String {
        val trimmedDestination = destination.trim().toLowerCase(Locale.ROOT)
        return when (trimmedDestination) {
            "manali" -> ""
            "shimla" -> ""
            // Add more details for other destinations
            else -> "Details not available for this destination."
        }
    }

    fun onShowDetailsClick(view: View) {
        // Additional logic for showing details directly without notification
        showDetailsDirectly()
    }

    private fun showDetailsDirectly() {
        // Implement logic to directly show details without a notification
        // For example, set the details in the second TextView
        findViewById<TextView>(R.id.detailsTextView).text = getDetailsForDestination(destination)
        Toast.makeText(this, "Details for $destination", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity2() {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }
}
