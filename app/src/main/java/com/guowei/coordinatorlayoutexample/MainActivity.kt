package com.guowei.coordinatorlayoutexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val simpleButton = findViewById<Button>(R.id.simple)
        simpleButton.setOnClickListener {
            val intent = Intent(this, SimpleActivity::class.java)
            startActivity(intent)
        }

        val footerButton = findViewById<Button>(R.id.footer)
        footerButton.setOnClickListener {
            val intent = Intent(this, FooterBarActivity::class.java)
            startActivity(intent)
        }
    }
}