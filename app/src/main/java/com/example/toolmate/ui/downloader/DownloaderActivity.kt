package com.example.toolmate.ui.downloader

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.toolmate.R
import com.example.toolmate.data.response.YoutubeResponse
import com.example.toolmate.data.retrofit.ApiConfig
import com.example.toolmate.databinding.ActivityDownloaderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DownloaderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDownloaderBinding

    companion object {
        const val NAME_PLATFORM = "nama_platform"
        const val LOGO_PLATFORM = "logo_platform"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDownloaderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvDownloader)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvPlatform.text = intent.getStringExtra(NAME_PLATFORM)
        val img = intent.getStringExtra(LOGO_PLATFORM)
        Glide.with(this@DownloaderActivity)
            .load(img)
            .into(binding.imgLogo)

        binding.btLink.setOnClickListener {
            setDownloaderVisibility(false)
            showLoading(true)
            getInfoMedia(binding.etLink.text.toString())
        }
    }

    private fun setDownloaderVisibility(visible: Boolean) {
        binding.imgMedia.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        binding.tvTitle.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        binding.tvTitleMedia.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        binding.tvDuration.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        binding.tvDurationMedia.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        binding.btnDownload.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    private fun showLoading(visible: Boolean) {
        binding.pbDownloader.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    private fun getInfoMedia(url: String) {
        val client = ApiConfig.getApiService().getYoutubeVideo(url)
        client.enqueue(object : Callback<YoutubeResponse> {
            override fun onResponse(
                call: Call<YoutubeResponse?>,
                response: Response<YoutubeResponse?>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Glide.with(this@DownloaderActivity)
                        .load(responseBody?.result?.metadata?.thumbnail)
                        .into(binding.imgMedia)
                    binding.tvTitleMedia.text = responseBody?.result?.title
                    binding.tvDurationMedia.text = responseBody?.result?.metadata?.duration
                    setDownloaderVisibility(true)
                }
            }

            override fun onFailure(
                p0: Call<YoutubeResponse?>,
                p1: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })
    }
}