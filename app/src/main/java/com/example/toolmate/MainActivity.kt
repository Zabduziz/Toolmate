package com.example.toolmate

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.toolmate.api.FileResponse
import com.example.toolmate.api.RetrofitClient
import com.example.toolmate.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

class SecondActivity : AppCompatActivity() {

    private lateinit var imgAppIcon: ImageView
    private lateinit var tvAppName: TextView
    private lateinit var etLink: EditText
    private lateinit var btnDownload: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        imgAppIcon = findViewById(R.id.imgAppIcon)
        tvAppName = findViewById(R.id.tvAppName)
        etLink = findViewById(R.id.etLink)
        btnDownload = findViewById(R.id.btnDownload)

        // Ambil data yang dikirim dari DashboardFragment
        val appName = intent.getStringExtra("APP_NAME")
        val appIcon = intent.getStringExtra("APP_ICON")

        // Set data ke tampilan
        tvAppName.text = appName
        Glide.with(this).load(appIcon).into(imgAppIcon)

        // Tombol Download untuk mengambil URL dan mulai download
        btnDownload.setOnClickListener {
            val link = etLink.text.toString()
            if (link.isNotEmpty()) {
                downloadContent(link, appName)
            } else {
                Toast.makeText(this, "Please paste a valid link", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadContent(link: String, appName: String?) {
        when (appName) {
            "Instagram" -> downloadFromInstagram(link)
            "Youtube" -> downloadFromYoutube(link)
            else -> Toast.makeText(this, "App not supported yet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadFromInstagram(link: String) {
        RetrofitClient.instance.downloadInstagram(link).enqueue(object : Callback<FileResponse> {
            override fun onResponse(call: Call<FileResponse>, response: Response<FileResponse>) {
                if (response.isSuccessful) {
                    val downloadUrl = response.body()?.downloadUrl
                    if (downloadUrl != null) {
                        downloadFile(downloadUrl)
                    }
                } else {
                    Toast.makeText(this@SecondActivity, "Failed to fetch download URL", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                Toast.makeText(this@SecondActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun downloadFromYoutube(link: String) {
        RetrofitClient.instance.downloadYoutube(link).enqueue(object : Callback<FileResponse> {
            override fun onResponse(call: Call<FileResponse>, response: Response<FileResponse>) {
                if (response.isSuccessful) {
                    val downloadUrl = response.body()?.downloadUrl
                    if (downloadUrl != null) {
                        downloadFile(downloadUrl)
                    }
                } else {
                    Toast.makeText(this@SecondActivity, "Failed to fetch download URL", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<FileResponse>, t: Throwable) {
                Toast.makeText(this@SecondActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun downloadFile(url: String) {
        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Downloading File")
            .setDescription("Downloading from the selected app")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "downloadedFile")

        downloadManager.enqueue(request)
        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()
    }
}