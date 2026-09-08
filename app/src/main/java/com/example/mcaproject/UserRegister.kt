package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_register)

        val login = findViewById<TextView>(R.id.login)

        login.setOnClickListener {
            val intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
            finish()

        }
    }
}