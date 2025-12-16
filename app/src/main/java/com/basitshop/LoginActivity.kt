package com.basitshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEt = findViewById<EditText>(R.id.et_email)
        val passEt = findViewById<EditText>(R.id.et_password)
        val loginBtn = findViewById<Button>(R.id.btn_login)
        val registerToggle = findViewById<TextView>(R.id.tv_register_toggle)

        loginBtn.setOnClickListener {
            val email = emailEt.text.toString()
            val password = passEt.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
                performLogin(email, password)
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        
        registerToggle.setOnClickListener {
             Toast.makeText(this, "Switching to Register Mode (Logic here)", Toast.LENGTH_SHORT).show()
             // Yahan tum Register ka logic laga sakte ho, ya text badal sakte ho
        }
    }

    private fun performLogin(email: String, pass: String) {
        // Simple Networking Thread (Termux friendly, no Retrofit complex setup needed for now)
        thread {
            try {
                // Using Config file URL
                val url = URL(Config.LOGIN_URL)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                
                // Sending Data...
                // (Yeh basic connection check hai)
                
                runOnUiThread {
                    Toast.makeText(this, "Connecting to ${Config.BASE_URL}...", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this, "Connection Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

