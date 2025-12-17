package com.basitshop

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.basitshop.utils.DisplayManager
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
    private lateinit var forgotPassword: TextView
    private lateinit var rememberCheck: CheckBox
    private lateinit var loginCard: View

    private lateinit var prefs: SharedPreferences

    @Volatile
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences("auth_prefs", MODE_PRIVATE)

        emailEt = findViewById(R.id.et_email)
        passEt = findViewById(R.id.et_password)
        loginBtn = findViewById(R.id.btn_login)
        registerToggle = findViewById(R.id.tv_register_toggle)
        forgotPassword = findViewById(R.id.tv_forgot_password)
        rememberCheck = findViewById(R.id.cb_remember)
        loginCard = findViewById(R.id.login_card)

        // Entry animation
        loginCard.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fade_slide_up)
        )

        // Display handling
        val displayInfo = DisplayManager.getDisplayInfo(this)
        when (displayInfo.deviceType) {
            DisplayManager.DeviceType.TABLET -> loginCard.scaleX = 0.92f
            DisplayManager.DeviceType.DESKTOP_DEX -> loginCard.scaleX = 0.85f
            else -> {}
        }

        // Restore remember me
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
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        isLoading = loading
        loginBtn.isEnabled = !loading
        loginBtn.alpha = if (loading) 0.6f else 1f
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}