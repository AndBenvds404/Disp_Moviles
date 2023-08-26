package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.connections.Gpt3API
import com.example.dispositivosmoviles.databinding.ActivityChatGptBinding

class ChatGptActivity : AppCompatActivity() {
    private var gpt3Api: Gpt3API? = null

    private val MESSAGE_TYPE_RESPONSE = "RESPONSE"
    private val MESSAGE_TYPE_REQUEST = "REQUEST"

    private lateinit var binding: ActivityChatGptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatGptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gpt3Api = Gpt3API(this)

        binding.buttonAsk.setOnClickListener {
            val prompt = binding.editTextQuestion.text.toString().trim()
            if (prompt.isNotEmpty()) {
                addChatMessage(prompt, MESSAGE_TYPE_REQUEST)
                binding.editTextQuestion.text.clear()
                binding.progressBar.visibility = View.VISIBLE

                gpt3Api?.generateText(prompt, { response ->
                    binding.progressBar.visibility = View.GONE
                    addChatMessage(response, MESSAGE_TYPE_RESPONSE)

                }, { error ->
                    binding.progressBar.visibility = View.GONE
                    addChatMessage("Error: ${error.message}", MESSAGE_TYPE_RESPONSE)
                })
            }
        }

        binding.btnBack.setOnClickListener{
            var intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun addChatMessage(message: String, type: String) {
        val textView = TextView(this)
        Log.i("addchat", message)
        textView.text = message
        if (MESSAGE_TYPE_RESPONSE == type) {

            val gradientDrawable = GradientDrawable()
            textView.setTypeface(null, Typeface.BOLD_ITALIC)
            textView.gravity = Gravity.END
            textView.setBackgroundColor(Color.argb(255,234, 234, 234))
            textView.setTextColor(Color.argb(100,0, 0, 0))
            textView.textSize = 14F




        }
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(30, 5, 30, 5)
        textView.layoutParams = params
        binding.layoutInput.addView(textView)

    }
}
