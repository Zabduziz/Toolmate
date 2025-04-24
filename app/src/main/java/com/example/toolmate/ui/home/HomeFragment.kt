package com.example.toolmate.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toolmate.AppAdapter
import com.example.toolmate.R
import com.example.toolmate.SecondActivity
import com.example.toolmate.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Pastikan `onCreateView` selalu mengembalikan View yang valid dan non-nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Mengambil data nama aplikasi dan ikon aplikasi dari resources
        val appNames = resources.getStringArray(R.array.app_names)
        val appIcons = resources.obtainTypedArray(R.array.app_icons)

        // Menyiapkan RecyclerView dan Adapter
        val recyclerView: RecyclerView = binding.gridRecyclerView
        val adapter = AppAdapter(requireContext(), appNames, appIcons)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter

        // Menambahkan listener untuk menangani klik pada item di RecyclerView
        adapter.setOnItemClickListener(object : AppAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Membuka SecondActivity saat item grid diklik
                val intent = Intent(requireActivity(), SecondActivity::class.java)
                intent.putExtra("app_name", appNames[position]) // Mengirim nama aplikasi yang dipilih
                startActivity(intent)
            }
        })

        // Mengembalikan root view yang sudah di-inflate
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Mengatur _binding ke null setelah fragment dihancurkan
    }
}
