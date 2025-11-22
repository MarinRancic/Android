package com.example.androidvjezbe

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var number1: EditText
    private lateinit var number2: EditText
    private lateinit var operatorSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_layout)

        number1 = findViewById(R.id.number1)
        number2 = findViewById(R.id.number2)
        operatorSpinner = findViewById(R.id.operatorSpinner)

        val btnCalculate = findViewById<Button>(R.id.btnCalculate)

        val operators = arrayOf("+", "-", "*", "/")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, operators)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        operatorSpinner.adapter = adapter

        btnCalculate.setOnClickListener {
            val num1Str = number1.text.toString()
            val num2Str = number2.text.toString()
            val operator = operatorSpinner.selectedItem.toString()

            if(num1Str.isEmpty() || num2Str.isEmpty()){
                Toast.makeText(this, "Molimo unesite oba broja", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val num1 = num1Str.toFloatOrNull()
            val num2 = num2Str.toFloatOrNull()

            if(num1 == null || num2 == null){
                Toast.makeText(this, "Neispravan broj", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val result: Float = when(operator){
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> {
                    if(num2 == 0f){
                        Toast.makeText(this, "Ne možete dijeliti sa nulom", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    } else num1 / num2
                }
                else -> 0f
            }

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("RESULT_VALUE", result)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()
        number1.text.clear()
        number2.text.clear()
        operatorSpinner.setSelection(0)
    }
}



/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidVjezbeTheme {
        Greeting("Androiddddd")
    }
}
*/