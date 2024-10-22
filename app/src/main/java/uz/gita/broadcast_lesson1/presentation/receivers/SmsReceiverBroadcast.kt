package uz.gita.broadcast_lesson1.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import uz.gita.broadcast_lesson1.R

/*
Broadcast receiver yozishda etiborli jihati shundaki, Android 8(API 26) dan boshlab ko`plab system actionlarni manifeystni o`zidan registr qilish kifoya emas. Ya`ni
https://developer.android.com/develop/background-work/background-tasks/broadcasts/broadcast-exceptions ushbu linkda ko`rsatilgan broadcastlardan boshqalarini manifeystda e`lon qilganimiz yetarli bo`lmaydi. Ular ishlamaydi.
 Ular ishlashi uchun esa Activityda registratsiya qilishimiz kerak
 */
class SmsReceiverBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.provider.Telephony.SMS_RECEIVED") { // it's best practice to verify intent action before performing any operation
            Log.i("ReceiverApp", "SMS Received")
        }
        MediaPlayer.create(context, R.raw.sound_1).start()
    }

}