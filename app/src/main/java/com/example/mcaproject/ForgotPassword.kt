package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)

        val login = findViewById<TextView>(R.id.tvLogin)

        login.setOnClickListener {
            val intent = Intent(this, AdminLogin::class.java)
            startActivity(intent)
            finish()

        }


        }
    }
