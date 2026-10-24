package com.example.mcaproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_login)


        val signup = findViewById<TextView>(R.id.signup)

        val forgot = findViewById<TextView>(R.id.txtForgotPassword)

        val farmerhome = findViewById<Button>(R.id.btnLogin)

        forgot.setOnClickListener {
            val intent  = Intent(this,ForgotPassword::class.java)
            startActivity(intent)

        }
        farmerhome.setOnClickListener {
            val intent  = Intent(this, Farmerhomescreen::class.java)
            startActivity(intent)

        }



        signup.setOnClickListener {
            val intent  = Intent(this, UserRegister::class.java)
            startActivity(intent)
            finish()

        }
    }
}