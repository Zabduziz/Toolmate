package com.example.toolmate.ui.downloader

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.toolmate.R
import com.example.toolmate.data.database.History
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
import com.example.toolmate.data.helper.DateHelper
import com.example.toolmate.data.helper.ViewModelFactory
import com.example.toolmate.databinding.ActivityDownloaderBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class DownloaderActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDownloaderBinding
    private lateinit var thumbnail: String
    private lateinit var downloaderViewModel: DownloaderViewModel
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

        val topAppBar: MaterialToolbar = binding.topAppBarDownloader
        setSupportActionBar(topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.downloaderRoot) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        downloaderViewModel = obtainViewModel(this@DownloaderActivity)

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
                showSaveAsDialog(this) {fileName ->
                    binding.btnDownload.isClickable = false
                    val downloader = AndroidDownloader(this, fileName)
                    for (x in linksDownload) {
                        Toast.makeText(this, "Start Downloading $fileName", Toast.LENGTH_SHORT).show()
                        downloader.downloadFile(x)
                    }
                    val history = History(
                        medianame = fileName,
                        datedownload = DateHelper.getCurrentDate(),
                        thumbnails = thumbnail
                    )
                    downloaderViewModel.insert(history as History)
                }
            }
            else -> TODO()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
                thumbnail = result.thumbnail.toString()
            } else {
                Toast.makeText(this, "Failed to fetch media info.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): DownloaderViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DownloaderViewModel::class.java)
    }

    fun showSaveAsDialog(context: Context, onAccept: (String) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_save_as, null)
        val editText = dialogView.findViewById<TextInputEditText>(R.id.etNameFile)

        val dialog = AlertDialog.Builder(context)
            .setTitle(R.string.dialog_title)
            .setView(dialogView)
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Accept") { _, _ ->
                val fileName = editText.text.toString().trim()
                if (fileName.isNotEmpty()) {
                    onAccept("$fileName.mp4")
                } else {
                    Toast.makeText(context, "Filename can't be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .create()

        dialog.show()
    }

}