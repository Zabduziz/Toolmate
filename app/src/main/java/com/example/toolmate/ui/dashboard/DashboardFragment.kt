package com.example.toolmate.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toolmate.ListAppAdapter
import com.example.toolmate.R
import com.example.toolmate.apps
import com.example.toolmate.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var rvApps: RecyclerView
    private val list = ArrayList<apps>()

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvApps = binding.rvApps
        rvApps.setHasFixedSize(true)

        list.addAll(getListApps())
        showRecycleList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getListApps(): ArrayList<apps> {
        val dataAppIcon = resources.getStringArray(R.array.data_apps)
        val dataApps = resources.getStringArray(R.array.data_apps)
        val listApp = ArrayList<apps>()
        for (i in dataApps.indices) {
            val apps = apps(dataApps[i], dataAppIcon[i])
            listApp.add(apps)
        }
        return listApp
    }

    private fun showRecycleList() {
        binding.rvApps.layoutManager = GridLayoutManager(requireContext(), 3)
        val listAppAdapter = ListAppAdapter(list)
        binding.rvApps.adapter = listAppAdapter
    }
}