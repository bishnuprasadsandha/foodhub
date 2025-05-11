package com.example.thefoodhub

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.thefoodhub.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val Locationlist = arrayOf("Angul","Berhampur","Bhubaneswar","Cuttack","Dhenkanal","Jharsuguda","Kalahandi","Puri","Rayagada","Sambalpur","Sundargarh")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Locationlist)
        val autoCompleteTextView = binding.listOfLocation
        autoCompleteTextView.setAdapter(adapter)
    }
}