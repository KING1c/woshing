package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivitySecond2Binding
import com.google.firebase.auth.FirebaseAuth

class SecondActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivitySecond2Binding
    private lateinit var user:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecond2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance()

        if (user.currentUser !=null){
            user.currentUser?.let {

                binding.tvUserEmail.text = it.email

            }
        }

        binding.btnSignOut.setOnClickListener{
            user.signOut()
            startActivity(
                Intent(this,
                    MainActivity::class.java
                )
            )
            finish()
        }
    }
}