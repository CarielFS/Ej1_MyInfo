package com.cafesa.ej1_myinfo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cafesa.ej1_myinfo.databinding.ActivitySplashScreenBinding
import kotlin.concurrent.thread

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thread {
            Thread.sleep(3000)
            val intent = Intent(this, Form::class.java)
            startActivity(intent)

            finish()
        }

    }



}