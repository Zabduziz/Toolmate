package com.example.toolmate.ui.downloader

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.toolmate.R
import com.example.toolmate.data.fetcher.MediaFetcher
import com.example.toolmate.data.fetcher.TiktokMediaFetcher
import com.example.toolmate.data.fetcher.YoutubeMediaFetcher
import com.example.toolmate.databinding.ActivityDownloaderBinding

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
            var mediaOption = binding.tvPlatform.text
            var etLink = binding.etLink.text.toString()
            setDownloaderVisibility(false)
            showLoading(true)
            when (mediaOption) {
                "Youtube" -> getInfoMedia(etLink, YoutubeMediaFetcher())
                "Instagram" -> TODO("Coming Soon")
                "Tiktok" -> getInfoMedia(etLink, TiktokMediaFetcher())
                else -> TODO("Kosong Cik")
            }
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

    private fun getInfoMedia(url: String, fetcher: MediaFetcher) {
        showLoading(true)
        fetcher.fetchMedia(url) { result ->
            showLoading(false)
            if (result != null) {
                Glide.with(this@DownloaderActivity)
                    .load(result.thumbnail)
                    .into(binding.imgMedia)
                binding.tvTitleMedia.text = result.title
                binding.tvDurationMedia.text = result.duration
                setDownloaderVisibility(true)
            } else {
                Toast.makeText(this, "Failed to fetch media info.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}