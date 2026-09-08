package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdminRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_register)

        val login = findViewById<TextView>(R.id.login)

        login.setOnClickListener {
            val intent = Intent(this, AdminLogin::class.java)
            startActivity(intent)
            finish()

        }
    }
}