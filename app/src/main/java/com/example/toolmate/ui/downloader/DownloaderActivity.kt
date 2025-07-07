package com.example.toolmate.ui.downloader

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.toolmate.R
import com.example.toolmate.data.downloadmanager.AndroidDownloader
import com.example.toolmate.data.fetcher.BstationMediaFetcher
import com.example.toolmate.data.fetcher.FacebookMediaFetcher
import com.example.toolmate.data.fetcher.InstagramMediaFetcher
import com.example.toolmate.data.fetcher.MediaFetcher
import com.example.toolmate.data.fetcher.SnackVideoMediaFetcher
import com.example.toolmate.data.fetcher.ThreadsMediaFetcher
import com.example.toolmate.data.fetcher.TiktokMediaFetcher
import com.example.toolmate.data.fetcher.TwitterMediaFetcher
import com.example.toolmate.data.fetcher.YoutubeMediaFetcher
import com.example.toolmate.databinding.ActivityDownloaderBinding

class DownloaderActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDownloaderBinding
    private var linksDownload = mutableListOf<String>()

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tvPlatform)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.tvPlatform.text = intent.getStringExtra(NAME_PLATFORM)
        binding.btLink.setOnClickListener(this)
        binding.btnDownload.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btLink -> {
                linksDownload.clear()
                var mediaOption = binding.tvPlatform.text
                var etLink = binding.etLink.text.toString()
                setDownloaderVisibility(false)
                showLoading(true)
                when (mediaOption) {
                    "Youtube" -> getInfoMedia(etLink, YoutubeMediaFetcher())
                    "Instagram" -> getInfoMedia(etLink, InstagramMediaFetcher())
                    "Tiktok" -> getInfoMedia(etLink, TiktokMediaFetcher())
                    "Twitter" -> getInfoMedia(etLink, TwitterMediaFetcher())
                    "Facebook" -> getInfoMedia(etLink, FacebookMediaFetcher())
                    "Threads" -> getInfoMedia(etLink, ThreadsMediaFetcher())
                    "SnackVideo" -> getInfoMedia(etLink, SnackVideoMediaFetcher())
                    "Bstation" -> getInfoMedia(etLink, BstationMediaFetcher())
                    else -> TODO("Kosong Cik")
                }
            }
            R.id.btnDownload -> {
                binding.btnDownload.isClickable = false
                val downloader = AndroidDownloader(this)
                for (x in linksDownload) {
                    downloader.downloadFile(x)
                }
            }
            else -> TODO()
        }
    }

    private fun setDownloaderVisibility(visible: Boolean) {
        binding.cardResult.visibility = if (visible) View.VISIBLE else View.INVISIBLE
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
                binding.tvMediaTitle.text = HtmlCompat.fromHtml(
                    result.title.toString(),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                binding.tvMediaDuration.text = result.duration
                linksDownload.addAll(result.links ?: emptyList())
                setDownloaderVisibility(true)
                binding.btnDownload.isClickable = true
            } else {
                Toast.makeText(this, "Failed to fetch media info.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}