package com.example.ca2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create an instance of CustomDrawing
        val customDrawing = CustomDrawing(this)

        // Set CustomDrawing as the content view for MainActivity2
        setContentView(customDrawing)

        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this, Alarm_Manager::class.java)
            startActivity(i)
            finish()
        }, 5000)
    }
}
