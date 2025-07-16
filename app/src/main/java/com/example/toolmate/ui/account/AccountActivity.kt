package com.example.toolmate.ui.account

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.toolmate.R
import com.example.toolmate.data.helper.ViewModelFactory
import com.example.toolmate.databinding.ActivityAccountBinding
import com.example.toolmate.ui.authentication.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var accountViewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        enableEdgeToEdge()
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left+24,
                systemBars.top+24,
                systemBars.right+24,
                systemBars.bottom+24
            )
            insets
        }
        accountViewModel = obtainViewModel(this@AccountActivity)
        binding.tvUsername.text = auth.currentUser?.uid
        binding.tvEmailUser.text = auth.currentUser?.email
        binding.btLogout.setOnClickListener {
            accountViewModel.deleteAllHistory()
            auth.signOut()
            Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btBack.setOnClickListener {
            finish()
        }
    }
    private fun obtainViewModel(activity: AppCompatActivity): AccountViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(AccountViewModel::class.java)
    }
}