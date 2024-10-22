package uz.gita.broadcast_lesson1.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import androidx.core.content.ContextCompat
import uz.gita.broadcast_lesson1.R
import uz.gita.broadcast_lesson1.presentation.receivers.ContextRegisteredReceiver
import uz.gita.broadcast_lesson1.presentation.receivers.NotificationDismissedReceiver

class ReceiverRegisterService : Service() {
    private val channelId = "my_service"
    private val channelName = "My Background Service"
    private val notificationID = 101
    override fun onBind(intent: Intent?): IBinder? = null
    private val broadcast = ContextRegisteredReceiver()
    override fun onCreate() {
        super.onCreate()
//        startForeground()
        //Contextda broadcastlarni registratsiya qilish uchun quidagi obyektlarni yaratib olamiz
        //Broadcast obyekti. Bu o`zimiz yaratib olgan class obyekti
        //filter- bu qaysi actionlarni uchlashini bildiradi
        val actionPowerConnected = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        val actionPowerDisconnected = IntentFilter(Intent.ACTION_POWER_DISCONNECTED)
        val actionScreenOn = IntentFilter(Intent.ACTION_SCREEN_ON)
        val actionScreenOff = IntentFilter(Intent.ACTION_SCREEN_OFF)
        val actionCustomBroadcast = IntentFilter("uz.gita.broadcast.CUSTOM_RECEIVER")
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
            actionCustomBroadcast,
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

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dismissIntent = Intent(this, NotificationDismissedReceiver::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            ""
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Your Service")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setDeleteIntent(dismissPendingIntent) // Handle notification removal
            .build()

        startForeground(notificationID, notification)

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show()
        unregisterReceiver(broadcast)

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
        startForeground(notificationID, notification)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(): String {
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