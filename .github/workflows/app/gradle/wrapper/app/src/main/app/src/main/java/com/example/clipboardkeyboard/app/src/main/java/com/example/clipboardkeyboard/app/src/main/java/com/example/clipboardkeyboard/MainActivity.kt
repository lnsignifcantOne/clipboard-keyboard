package com.example.clipboardkeyboard

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var storage: ClipStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val root = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val btnEnable = Button(this).apply { text = "1. Enable Keyboard in Settings" }
        btnEnable.setOnClickListener {
            startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
        }

        val btnSelect = Button(this).apply { text = "2. Select Keyboard" }
        btnSelect.setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
            imm.showInputMethodPicker()
        }

        val etNewClip = EditText(this).apply { hint = "Enter phrase to add..." }
        val btnAdd = Button(this).apply { text = "Add Phrase" }

        root.addView(btnEnable)
        root.addView(btnSelect)
        root.addView(etNewClip)
        root.addView(btnAdd)

        setContentView(root)

        storage = ClipStorage(this)
        btnAdd.setOnClickListener {
            val text = etNewClip.text.toString().trim()
            if (text.isNotEmpty()) {
                storage.addClip(text)
                etNewClip.setText("")
            }
        }
    }
}

