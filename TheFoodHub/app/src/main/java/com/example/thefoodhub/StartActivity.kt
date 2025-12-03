package com.example.thefoodhub

import android.content.Intent
import android.os.Bundle
import androidx.core.view.WindowInsetsControllerCompat
import androidx.appcompat.app.AppCompatActivity
import com.example.thefoodhub.databinding.ActivityStartBinding


class StartActivity : AppCompatActivity(){
private val binding:ActivityStartBinding by lazy {
    ActivityStartBinding.inflate(layoutInflater)
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)

        window.statusBarColor = getColor(R.color.white)
        windowInsetsController.isAppearanceLightStatusBars = true

        binding.nextButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}