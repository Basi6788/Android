package com.basitshop

import android.content.Intent
<<<<<<< HEAD
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.basitshop.utils.DisplayManager
import java.io.BufferedWriter
import java.io.OutputStreamWriter
=======
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
>>>>>>> 317de5e (update)
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

<<<<<<< HEAD
    private lateinit var emailEt: EditText
    private lateinit var passEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerToggle: TextView
    private lateinit var forgotPassword: TextView
    private lateinit var rememberCheck: CheckBox
    private lateinit var loginCard: View

    private lateinit var prefs: SharedPreferences

    @Volatile
    private var isLoading = false

=======
>>>>>>> 317de5e (update)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

<<<<<<< HEAD
        prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE)

        emailEt = findViewById(R.id.et_email)
        passEt = findViewById(R.id.et_password)
        loginBtn = findViewById(R.id.btn_login)
        registerToggle = findViewById(R.id.tv_register_toggle)
        forgotPassword = findViewById(R.id.tv_forgot_password)
        rememberCheck = findViewById(R.id.cb_remember)
        loginCard = findViewById(R.id.login_card)

        // ===== Entry Animation =====
        loginCard.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fade_slide_up)
        )

        // ===== DisplayManager Apply =====
        val displayInfo = DisplayManager.getDisplayInfo(this)
        when (displayInfo.deviceType) {
            DisplayManager.DeviceType.TABLET -> loginCard.scaleX = 0.92f
            DisplayManager.DeviceType.DESKTOP_DEX -> loginCard.scaleX = 0.85f
            else -> {}
        }

        // ===== Remember Me Restore =====
        emailEt.setText(prefs.getString("saved_email", ""))
        rememberCheck.isChecked = prefs.getBoolean("remember", false)

        loginBtn.setOnClickListener {
            if (isLoading) return@setOnClickListener

            val email = emailEt.text.toString().trim()
            val password = passEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                toast("Fill all fields")
                return@setOnClickListener
            }

            if (rememberCheck.isChecked) {
                prefs.edit()
                    .putString("saved_email", email)
                    .putBoolean("remember", true)
                    .apply()
            } else {
                prefs.edit().clear().apply()
            }

            performLogin(email, password)
        }

        registerToggle.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.fade_slide_right, android.R.anim.fade_out)
        }

        forgotPassword.setOnClickListener {
            val email = emailEt.text.toString().trim()
            if (email.isEmpty()) {
                toast("Enter email first")
            } else {
                performForgotPassword(email)
            }
=======
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
>>>>>>> 317de5e (update)
        }
    }

    private fun performLogin(email: String, pass: String) {
<<<<<<< HEAD
        setLoading(true)

        thread {
            var conn: HttpURLConnection? = null
            try {
                val url = URL(Config.LOGIN_URL)
                conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true
                conn.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded"
                )

                val body = "email=$email&password=$pass"

                BufferedWriter(OutputStreamWriter(conn.outputStream)).use {
                    it.write(body)
                    it.flush()
                }

                val code = conn.responseCode

                runOnUiThread {
                    setLoading(false)
                    if (code in 200..299) {
                        toast("Login successful")
                        // TODO: Navigate to Home
                    } else {
                        toast("Login failed ($code)")
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    setLoading(false)
                    toast("Error: ${e.message}")
                }
            } finally {
                conn?.disconnect()
            }
        }
    }

    private fun performForgotPassword(email: String) {
        thread {
            try {
                val url = URL("${Config.BASE_URL}/api/forgot-password")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.doOutput = true

                BufferedWriter(OutputStreamWriter(conn.outputStream)).use {
                    it.write("email=$email")
                    it.flush()
                }

                runOnUiThread {
                    toast("Password reset link sent")
                }
            } catch (e: Exception) {
                runOnUiThread {
                    toast("Failed to send reset email")
=======
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
>>>>>>> 317de5e (update)
                }
            }
        }
    }
<<<<<<< HEAD

    private fun setLoading(loading: Boolean) {
        isLoading = loading
        loginBtn.isEnabled = !loading
        loginBtn.alpha = if (loading) 0.6f else 1f
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
=======
}

>>>>>>> 317de5e (update)
