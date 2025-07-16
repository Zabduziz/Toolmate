package com.example.toolmate.ui.history

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.toolmate.R
import com.example.toolmate.data.adapter.HistoryAdapter
import com.example.toolmate.data.database.History
import com.example.toolmate.data.helper.ViewModelFactory
import com.example.toolmate.databinding.FragmentHistoryBinding
import com.google.firebase.firestore.FirebaseFirestore

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private lateinit var adapter: HistoryAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyViewModel = obtainViewModel()
        adapter = HistoryAdapter()

        adapter.setOnItemClickCallback(object : HistoryAdapter.OnItemClickCallback {
            override fun onItemClicked(history: History) {
                showDeleteDialog(history, history.downloadlink.toString())
            }
        })

        adapter.setOnItemLongClickCallback(object : HistoryAdapter.OnItemLongClickCallback {
            override fun onItemLongClicked(history: History) {
                adapter.toggleSelection(history)
                updateMultiDeleteUI()
            }
        })

        historyViewModel.getAllHistory().observe(viewLifecycleOwner) { historyList ->
            if (historyList != null) {
                adapter.setListHistory(historyList)
            }
            Log.d("Room Data", "Histories: $historyList")
        }

        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.setHasFixedSize(true)
        binding.rvHistory.adapter = adapter

        binding.fabDeleteSelected.setOnClickListener {
            val selected = adapter.getSelectedItem()
            if (selected.isNotEmpty()) {
                showMultiDeleteDialog(selected)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun obtainViewModel(): HistoryViewModel {
        val factory = ViewModelFactory.getInstance(requireActivity().application)
        return ViewModelProvider(this, factory).get(HistoryViewModel::class.java)
    }

    private fun showDeleteDialog(history: History, linkdownload: String) {
        val view = layoutInflater.inflate(R.layout.dialog_selectable_text, null)
        val tvLinkText = view.findViewById<TextView>(R.id.tv_link_download)
        tvLinkText.text = linkdownload
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Delete History")
            .setView(view)
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("Yes") { _, _ ->
                deleteFromFirestore(history)
                obtainViewModel().delete(history)
            }
            .create()
        dialog.show()
    }

    private fun showMultiDeleteDialog(selected: List<History>) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete")
        builder.setMessage("Delete ${selected.size} items?")
        builder.setPositiveButton("Yes") { _, _ ->
            val vm = obtainViewModel()
            selected.forEach {
                deleteFromFirestore(it)
                vm.delete(it)
            }
            adapter.clearSelection()
            updateMultiDeleteUI()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun updateMultiDeleteUI() {
        val visible = adapter.getSelectedItem().isNotEmpty()
        binding.fabDeleteSelected.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun deleteFromFirestore(history: History) {
        val db = FirebaseFirestore.getInstance()
        db.collection("history")
            .whereEqualTo("datedownload", history.datedownload)
            .get()
            .addOnSuccessListener { querysnapshot ->
                for (document in querysnapshot) {
                    db.collection("history")
                        .document(document.id)
                        .delete()
                }
                Log.d(TAG, "DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error Finding Document", e)
            }
    }
}