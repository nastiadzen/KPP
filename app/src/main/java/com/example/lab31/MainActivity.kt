package com.example.lab31

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.util.Base64

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
            // Знаходимо необхідні елементи
            val inputText = findViewById<EditText>(R.id.inputText)
            val encryptButton = findViewById<Button>(R.id.encryptButton)
            val decryptButton = findViewById<Button>(R.id.decryptButton)
            val encryptedText = findViewById<TextView>(R.id.encryptedText)
            val decryptedText = findViewById<TextView>(R.id.decryptedText)

            // Логіка для кнопки "Зашифрувати"
            encryptButton.setOnClickListener {
                val textToEncrypt = inputText.text.toString()
                if (textToEncrypt.isNotEmpty()) {
                    val encrypted = encryptText(textToEncrypt)
                    encryptedText.text = encrypted
                } else {
                    encryptedText.text = "Поле вводу порожнє"
                }
            }

        // Логіка для кнопки "Розшифрувати"
        decryptButton.setOnClickListener {
            val encrypted = encryptedText.text.toString()
            if (encrypted.startsWith("Зашифровано: ")) {
                val decrypted = decryptText(encrypted.removePrefix("Зашифровано: "))
                decryptedText.text = decrypted
            } else {
                decryptedText.text = "Немає зашифрованого тексту"
            }
        }
    }

        // Шифрування тексту
        @RequiresApi(Build.VERSION_CODES.O)
        private fun encryptText(text: String): String {
            val encryptedBytes = Base64.getEncoder().encode(text.toByteArray())
            return "Зашифровано: ${String(encryptedBytes)}"
        }

        // Дешифрування тексту
        @RequiresApi(Build.VERSION_CODES.O)
        private fun decryptText(encryptedText: String): String {
            return try {
                val decodedBytes = Base64.getDecoder().decode(encryptedText)
                String(decodedBytes)
            } catch (e: IllegalArgumentException) {
                "Помилка дешифрування"
            }
        }
    }

