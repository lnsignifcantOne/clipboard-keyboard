package com.example.clipboardkeyboard

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class ClipboardInputMethodService : InputMethodService() {

    private lateinit var storage: ClipStorage

    override fun onCreateInputView(): View {
        storage = ClipStorage(this)
        val view = layoutInflater.inflate(R.layout.keyboard_view, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvClips)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        
        val clips = storage.getClips()
        recyclerView.adapter = ClipAdapter(clips) { selectedText ->
            commitTextToInput(selectedText)
        }

        view.findViewById<ImageButton>(R.id.btnBackspace).setOnClickListener {
            currentInputConnection?.deleteSurroundingText(1, 0)
        }

        view.findViewById<ImageButton>(R.id.btnSpace).setOnClickListener {
            commitTextToInput(" ")
        }

        view.findViewById<ImageButton>(R.id.btnEnter).setOnClickListener {
            currentInputConnection?.sendKeyEvent(
                android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER)
            )
        }

        view.findViewById<ImageButton>(R.id.btnSwitchKeyboard).setOnClickListener {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showInputMethodPicker()
        }

        return view
    }

    private fun commitTextToInput(text: String) {
        currentInputConnection?.commitText(text, 1)
    }

    private inner class ClipAdapter(
        private val items: List<String>,
        private val onClick: (String) -> Unit
    ) : RecyclerView.Adapter<ClipAdapter.ViewHolder>() {

        inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
            val text: TextView = v.findViewById(R.id.tvClipText)
            val card: MaterialCardView = v.findViewById(R.id.cardTile)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = layoutInflater.inflate(R.layout.item_clip_key, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.text.text = item
            holder.card.setOnClickListener { onClick(item) }
        }

        override fun getItemCount() = items.size
    }
}

