package com.example.toolmate

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SecondActivity : AppCompatActivity() {

    private lateinit var imgAppIcon: ImageView
    private lateinit var tvAppName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        imgAppIcon = findViewById(R.id.imgAppIcon)
        tvAppName = findViewById(R.id.tvAppName)

        // Ambil data yang dikirim dari DashboardFragment
        val appName = intent.getStringExtra("APP_NAME")
        val appIcon = intent.getStringExtra("APP_ICON")

        // Set data ke tampilan
        tvAppName.text = appName
        Glide.with(this).load(appIcon).into(imgAppIcon)
    }
}
