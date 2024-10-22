package uz.gita.broadcast_lesson1.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.content.ContextCompat
import uz.gita.broadcast_lesson1.R
import uz.gita.broadcast_lesson1.presentation.receivers.ContextRegisteredReceiver

class ReceiverRegisterService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForeground()
        //Contextda broadcastlarni registratsiya qilish uchun quidagi obyektlarni yaratib olamiz
        //Broadcast obyekti. Bu o`zimiz yaratib olgan class obyekti
        val broadcast = ContextRegisteredReceiver()
        //filter- bu qaysi actionlarni uchlashini bildiradi
        val actionPowerConnected = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        val actionPowerDisconnected = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)
        val actionScreenOn = IntentFilter(Intent.ACTION_SCREEN_ON)
        val actionScreenOff = IntentFilter(Intent.ACTION_SCREEN_OFF)
        //receiverFlag - bu agar broadcast boshqa applar orqali kelish yoki kelmasligini bildiradi
        val receiverFlag = ContextCompat.RECEIVER_EXPORTED

        ContextCompat.registerReceiver(
            this,
            broadcast,
            actionPowerConnected,
            receiverFlag
        )
        ContextCompat.registerReceiver(
            this,
            broadcast,
            actionPowerDisconnected,
            receiverFlag
        )
        ContextCompat.registerReceiver(
            this,
            broadcast,
            actionScreenOn,
            receiverFlag
        )
        ContextCompat.registerReceiver(
            this,
            broadcast,
            actionScreenOff,
            receiverFlag
        )
    }

    private fun startForeground() {

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            } else {
                // If earlier version channel ID is not used
                // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(PRIORITY_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
        startForeground(101, notification)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
        val channelId = "my_service"
        val channelName = "My Background Service"
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_HIGH
        )
        chan.lightColor = Color.BLUE
        chan.importance = NotificationManager.IMPORTANCE_NONE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

}