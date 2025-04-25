package com.example.toolmate

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.widget.Spinner
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.widget.ArrayAdapter
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
    //private lateinit var spinnerQuality: Spinner

    //private val qualityOptions = arrayOf("360", "480", "720", "1080")

    private val appApiMap = mapOf<String, (String) -> Call<FileResponse>>(
        "Youtube" to { url: String -> RetrofitClient.api.downloadYoutubeVideo(url) },
        "Instagram" to { url: String -> RetrofitClient.api.downloadInstagramVideo(url) },
        "Tiktok" to { url: String -> RetrofitClient.api.downloadTiktokVideo(url) },
        "Twitter" to { url: String -> RetrofitClient.api.downloadTwitterVideo(url) },
        "Facebook" to { url: String -> RetrofitClient.api.downloadFacebookVideo(url) },
        "SnackVideo" to { url: String -> RetrofitClient.api.downloadSnackVideo(url) },
        "Spotify" to { url: String -> RetrofitClient.api.downloadSpotifyTrack(url) },
        "Soundcloud" to { url: String -> RetrofitClient.api.downloadSoundcloudTrack(url) },
        "Apple Music" to { url: String -> RetrofitClient.api.downloadAppleMusicTrack(url) },
        "Terabox" to { url: String -> RetrofitClient.api.downloadTeraboxFile(url) },
        "MediaFire" to { url: String -> RetrofitClient.api.downloadMediaFireFile(url) },
        "Bstation" to { url: String -> RetrofitClient.api.downloadBstationFile(url) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        imgAppIcon = findViewById(R.id.imgAppIcon)
        tvAppName = findViewById(R.id.tvAppName)
        etLink = findViewById(R.id.etLink)
        //spinnerQuality = findViewById(R.id.spinnerQuality)
        btnDownload = findViewById(R.id.btnDownload)

        //val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, qualityOptions)
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        //spinnerQuality.adapter = adapter


        val appName = intent.getStringExtra("APP_NAME")
        val appIcon = intent.getStringExtra("APP_ICON")

        tvAppName.text = appName
        Glide.with(this).load(appIcon).into(imgAppIcon)

        // In onCreate of SecondActivity
//        if (appName != "Youtube") {
//            spinnerQuality.visibility = android.view.View.GONE
//        } else {
//            spinnerQuality.visibility = android.view.View.VISIBLE
//        }



        btnDownload.setOnClickListener {
            val link = etLink.text.toString()

            // If link is empty, show error
            if (link.isNotEmpty() && appName != null) {
                downloadContent(link, appName)
            } else {
                Toast.makeText(this, "Please paste a valid link", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun downloadContent(link: String, appName: String) {
        val appDownloadMethod = appApiMap[appName]

        if (appDownloadMethod != null) {
            val call: Call<FileResponse> = appDownloadMethod(link) // No need to pass quality anymore

            // Make the network call
            call.enqueue(object : Callback<FileResponse> {
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
        } else {
            Toast.makeText(this, "App not supported yet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadFile(url: String) {
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(android.net.Uri.parse(url))
            .setTitle("Downloading Video")
            .setDescription("Downloading content")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(android.os.Environment.DIRECTORY_DOWNLOADS, "downloadedFile.mp4")

        downloadManager.enqueue(request)
        Toast.makeText(this, "Download started", Toast.LENGTH_SHORT).show()
    }
}