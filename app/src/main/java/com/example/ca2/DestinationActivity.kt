package com.example.ca2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DestinationActivity : AppCompatActivity() {

    private lateinit var selectedDate: Calendar
    private lateinit var selectedTime: Calendar
    private val jobSchedulerHelper = JobSchedulerHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination)

        // Initialize selectedDate and selectedTime with current date and time
        selectedDate = Calendar.getInstance()
        selectedTime = Calendar.getInstance()

        val pickDateButton = findViewById<Button>(R.id.pickDateButton)
        val pickTimeButton = findViewById<Button>(R.id.pickTimeButton)
        val showDetailsButton = findViewById<Button>(R.id.showDetailsButton)

        pickDateButton.setOnClickListener {
            showDatePickerDialog()
        }

        pickTimeButton.setOnClickListener {
            showTimePickerDialog()
        }

        showDetailsButton.setOnClickListener {
            if (!::selectedDate.isInitialized || !::selectedTime.isInitialized) {
                showToast("First select date and time of trip.")
            } else {
                val destination = intent.getStringExtra("destination") ?: ""

                // Schedule the job with the destination
                jobSchedulerHelper.scheduleJob(this, destination)

                // Navigate to TripDetailsActivity
                navigateToTripDetails(destination)

                // Show notification for the scheduled trip
                showNotification(destination)
            }
        }
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, monthOfYear, dayOfMonth ->
                selectedDate.set(year, monthOfYear, dayOfMonth)
                updateDateButtonText()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                updateTimeButtonText()
            },
            selectedTime.get(Calendar.HOUR_OF_DAY),
            selectedTime.get(Calendar.MINUTE),
            false // 24-hour format
        )
        timePickerDialog.show()
    }

    private fun updateDateButtonText() {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<Button>(R.id.pickDateButton).text = dateFormat.format(selectedDate.time)
    }

    private fun updateTimeButtonText() {
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        findViewById<Button>(R.id.pickTimeButton).text = timeFormat.format(selectedTime.time)
    }

    private fun navigateToTripDetails(destination: String) {
        val intent = Intent(this, TripDetailsActivity::class.java)
        intent.putExtra("destination", destination)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showNotification(destination: String) {
        createNotificationChannel()

        val notificationBuilder = NotificationCompat.Builder(this, "trip_notification_channel")
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Trip Scheduled")
            .setContentText("Your trip to $destination is scheduled.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(1, notificationBuilder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Trip Notifications"
            val descriptionText = "Notification channel for trip reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("trip_notification_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
