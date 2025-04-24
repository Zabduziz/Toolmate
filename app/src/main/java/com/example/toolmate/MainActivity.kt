package com.example.toolmate

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toolmate.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi BottomNavigationView
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Pengaturan AppBar untuk navigasi
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Mengambil data nama aplikasi dan ikon aplikasi dari resources
        val appNames = resources.getStringArray(R.array.app_names)
        val appIcons = resources.obtainTypedArray(R.array.app_icons)

        // Menyiapkan RecyclerView dan Adapter
        val recyclerView: RecyclerView = findViewById(R.id.gridRecyclerView) // Pastikan ID sesuai
        val adapter = AppAdapter(this, appNames, appIcons)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // Menampilkan dalam 3 kolom
        recyclerView.adapter = adapter

        // Menambahkan listener untuk menangani klik pada item di RecyclerView
        adapter.setOnItemClickListener(object : AppAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Membuka SecondActivity saat item grid diklik
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("app_name", appNames[position]) // Mengirim nama aplikasi yang dipilih
                startActivity(intent)
            }
        })
    }
}