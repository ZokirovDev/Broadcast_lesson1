package uz.gita.broadcast_lesson1

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import uz.gita.broadcast_lesson1.presentation.receivers.ContextRegisteredReceiver
import uz.gita.broadcast_lesson1.ui.theme.Broadcast_lesson1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
                LocalContext.current,
                broadcast,
                actionPowerConnected,
                receiverFlag
            )
            ContextCompat.registerReceiver(
                LocalContext.current,
                broadcast,
                actionPowerDisconnected,
                receiverFlag
            )
            ContextCompat.registerReceiver(
                LocalContext.current,
                broadcast,
                actionScreenOn,
                receiverFlag
            )
            ContextCompat.registerReceiver(
                LocalContext.current,
                broadcast,
                actionScreenOff,
                receiverFlag
            )

            Broadcast_lesson1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Broadcast_lesson1Theme {
        Greeting("Android")
    }
}