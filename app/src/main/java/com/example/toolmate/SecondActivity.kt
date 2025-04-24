package com.example.toolmate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val appName = intent.getStringExtra("app_name") // Mengambil nama aplikasi dari intent
        val textView: TextView = findViewById(R.id.appNameTextView)
        textView.text = "Selected App: $appName"  // Menampilkan nama aplikasi
    }
}
