package com.example.androidvjezbe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity

class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.result_layout)

        val result = findViewById<TextView>(R.id.result)
        val resultF = intent.getFloatExtra("RESULT_VALUE", 0f)
        result.text = "Rezultat: $resultF"

        val btnBack = findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }
    }
}