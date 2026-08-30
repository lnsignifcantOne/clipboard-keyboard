package com.example.clipboardkeyboard

import android.content.Context
import android.content.SharedPreferences

class ClipStorage(context: Context) {
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("clipboard_prefs", Context.MODE_PRIVATE)

    fun getClips(): List<String> {
        val saved = prefs.getStringSet("clips", null)
        return saved?.toList() ?: listOf(
            "owo", "wh", "wb", "owo pray",
            "hnsd", "wboss", "wsetteam 2", "nboss mn",
            "hnsc 20m", "hnsb", "wxp", "wtm add"
        )
    }

    fun addClip(text: String) {
        val current = getClips().toMutableSet()
        current.add(text)
        prefs.edit().putStringSet("clips", current).apply()
    }

    fun removeClip(text: String) {
        val current = getClips().toMutableSet()
        current.remove(text)
        prefs.edit().putStringSet("clips", current).apply()
    }
}

