package com.basitshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Ye line activity_login.xml ko load karti hai
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<TextInputEditText>(R.id.et_email)
        val passwordEditText = findViewById<TextInputEditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val registerTab = findViewById<TextView>(R.id.tab_register)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email aur Password dono daalein.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Yahan Retrofit ya Ktor se Back-end API ko call karna hai
            // Ek dummy check:
            if (email == "admin@bs.com" && password == "123456") {
                Toast.makeText(this, "Admin Login Successful!", Toast.LENGTH_LONG).show()
                // Home Screen ya Admin Dashboard pe Redirect karein
                // startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(this, "Login Failed: Invalid credentials.", Toast.LENGTH_SHORT).show()
            }
        }

        registerTab.setOnClickListener {
            // Register Activity par navigate karein (jo hum aage banayenge)
            // startActivity(Intent(this, RegisterActivity::class.java))
            Toast.makeText(this, "Going to Register Screen...", Toast.LENGTH_SHORT).show()
        }
    }
}

