package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Next : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_next)

        val admin = findViewById<Button>(R.id.btn_admin)

        val user = findViewById<Button>(R.id.btn_user)

        user.setOnClickListener {
            val intent = Intent(this,UserRegister::class.java)
            startActivity(intent)

        }

        admin.setOnClickListener {
            val intent = Intent(this,AdminRegister::class.java)
            startActivity(intent)

        }
    }
}