package com.example.toolmate.ui.authentication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.toolmate.MainActivity
import com.example.toolmate.R
import com.example.toolmate.data.database.History
import com.example.toolmate.data.helper.ViewModelFactory
import com.example.toolmate.databinding.ActivityLoginBinding
import com.example.toolmate.ui.account.AccountViewModel
import com.example.toolmate.ui.authentication.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left+24,
                systemBars.top+24,
                systemBars.right+24,
                systemBars.bottom+24
            )
            insets
        }

        loginViewModel = obtainViewModel(this@LoginActivity)
        auth = FirebaseAuth.getInstance()

        Glide.with(this@LoginActivity)
            .load(getString(R.string.logo_welcome))
            .into(binding.imgWelcomeLogin)

        binding.btSignIn.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btLogin.setOnClickListener {
            loginViewModel.deleteAllHistory()
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()
            // Email and Password Validation
            if (email.isEmpty()) {
                binding.etEmailLogin.error = "Email is required"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.etPasswordLogin.error = "Password is required"
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.etPasswordLogin.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.etEmailLogin.error = "Invalid email format"
                return@setOnClickListener
            }

            loginFirebase(email, password)
        }
        binding.tvContinueAsGuest.setOnClickListener {
            auth.signInAnonymously()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this, "Welcome ${auth.currentUser}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInAnonymously:failure", task.exception)
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Welcome ${auth.currentUser?.uid}", Toast.LENGTH_SHORT).show()
                    insertAllHistory(auth.currentUser?.uid.toString())
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun obtainViewModel(activity: AppCompatActivity): LoginViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
    }
    private fun insertAllHistory(userid: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("history")
            .whereEqualTo("userid", userid)
            .get()
            .addOnSuccessListener { result ->
                val historyList = mutableListOf<History>()
                for (document in result) {
                    val medianame = document.getString("medianame")
                    val datedownload = document.getString("datedownload")
                    val downloadlink = document.getString("downloadlink")
                    val thumbnails = document.getString("thumbnails")
                    val userid = document.getString("userid")

                    val history = History(
                        medianame = medianame,
                        datedownload = datedownload,
                        downloadlink = downloadlink,
                        thumbnails = thumbnails,
                        userid = userid
                    )
                    historyList.add(history)
                }
                loginViewModel.insertAllHistory(historyList)
                Log.d("Firestore", "Data inserted successfully")
                Log.d("Firestore", "Data: $historyList")
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error: ", e)
            }
    }
}