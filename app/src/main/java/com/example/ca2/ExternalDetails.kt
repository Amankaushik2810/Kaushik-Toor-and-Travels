package com.example.ca2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException

class ExternalDetails : AppCompatActivity() {
    lateinit var fstream: FileInputStream
    private val filename = "SampleFile.txt"
    private val filepath = "MyFileStorage"
    lateinit var myExternalFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_external_details)


            val result = findViewById<TextView>(R.id.resultView)
            val back = findViewById<Button>(R.id.btnBack)
            try {
                myExternalFile = File(getExternalFilesDir(filepath), filename)
                fstream = FileInputStream(myExternalFile)
                val sbuffer = StringBuffer()
                var i: Int
                while (fstream.read().also { i = it } != -1) {
                    sbuffer.append(i.toChar())
                }
                fstream.close()
                val details = sbuffer.toString().split("\n").toTypedArray()
                result.text = """
Name: ${details[0]}
Password: ${details[1]}
""".trimIndent()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            back.setOnClickListener {
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
    }
}