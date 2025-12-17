package com.basitshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEt: EditText
    private lateinit var passEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerToggle: TextView

    @Volatile
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEt = findViewById(R.id.et_email)
        passEt = findViewById(R.id.et_password)
        loginBtn = findViewById(R.id.btn_login)
        registerToggle = findViewById(R.id.tv_register_toggle)

        loginBtn.setOnClickListener {
            if (isLoading) return@setOnClickListener

            val email = emailEt.text.toString().trim()
            val password = passEt.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                toast("Fill all fields")
                return@setOnClickListener
            }

            performLogin(email, password)
        }

        registerToggle.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun performLogin(email: String, pass: String) {
        setLoading(true)

        thread {
            var conn: HttpURLConnection? = null
            try {
                val url = URL(Config.LOGIN_URL)
                conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.connectTimeout = 10000
                conn.readTimeout = 10000
                conn.doInput = true
                conn.doOutput = true
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                // Basic form body (backend friendly)
                val body = "email=$email&password=$pass"

                BufferedWriter(OutputStreamWriter(conn.outputStream, "UTF-8")).use {
                    it.write(body)
                    it.flush()
                }

                val responseCode = conn.responseCode

                runOnUiThread {
                    setLoading(false)

                    if (responseCode in 200..299) {
                        toast("Login successful")

                        // TODO: Navigate to Home / Dashboard
                        // startActivity(Intent(this, HomeActivity::class.java))
                        // finish()

                    } else {
                        toast("Login failed ($responseCode)")
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    setLoading(false)
                    toast("Connection error: ${e.message}")
                }
            } finally {
                conn?.disconnect()
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        isLoading = loading
        runOnUiThread {
            loginBtn.isEnabled = !loading
            loginBtn.alpha = if (loading) 0.6f else 1f
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}