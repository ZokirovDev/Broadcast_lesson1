package uz.gita.broadcast_lesson1.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import java.util.Locale

class ContextRegisteredReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            Intent.ACTION_POWER_CONNECTED-> speakText(context,"Power connected")
            Intent.ACTION_POWER_DISCONNECTED-> speakText(context,"Power disconnected")
            Intent.ACTION_SCREEN_ON-> speakText(context,"Screen on")
            Intent.ACTION_SCREEN_OFF-> speakText(context,"Screen off")
            else->speakText(context,"${intent?.action}  and data is ${intent?.getStringExtra("data")}")
        }

    }


    private fun speakText(context: Context?, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        var tts: TextToSpeech? = null
        val textToSpeech = TextToSpeech(context) { status ->
            Log.d("TTT", "getTextToSpeech: $status")
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale.US

                tts?.speak(text, TextToSpeech.QUEUE_ADD, null, "")

            }
        }
        tts = textToSpeech

    }

}

