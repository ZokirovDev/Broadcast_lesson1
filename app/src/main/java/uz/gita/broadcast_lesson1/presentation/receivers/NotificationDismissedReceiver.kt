package uz.gita.broadcast_lesson1.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import uz.gita.broadcast_lesson1.presentation.service.ReceiverRegisterService

class NotificationDismissedReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.stopService(Intent(context, ReceiverRegisterService::class.java))
    }

}