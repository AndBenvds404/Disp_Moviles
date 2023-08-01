package com.example.dispositivosmoviles.ui.utilities

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.ui.activities.CameraActivity

class BroadCasterNotifications: BroadcastReceiver (){
    override fun onReceive(context: Context, intent: Intent?) {

        val CHANNEL: String = "Notificaciones"
        val myIntent = Intent(context, Notification::class.java)


        val intent = Intent(context, CameraActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notif = NotificationCompat.Builder(context, CHANNEL)
        //cuerpo de Notificacion
        notif.setContentTitle("Titulo")
        notif.setContentText("No te olvides de parpadear ni de tomar awita <3")
        notif.setSmallIcon(R.drawable.baseline_favorite_24)
        //prioridad de la notificacion
        notif.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        notif.setStyle(
            NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line...")
        )
        //aqui se toca la notificacion.
        notif.setContentIntent(pendingIntent)
        notif.setAutoCancel(true)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)as NotificationManager
        notificationManager.notify(
            1,
            notif.build()
        )


    }


}