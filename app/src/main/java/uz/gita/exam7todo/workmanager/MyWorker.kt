package uz.gita.exam7todo.workmanager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat.*
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import uz.gita.exam7todo.MainActivity
import uz.gita.exam7todo.R

/**
 *    Created by Kamolov Samandar on 03.06.2023 at 19:31
 */

class MyWorker(val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("TTT", "doWork() is running!")
        val title = inputData.getString("title")
        val topic = inputData.getString("topic")
        val notificationId = inputData.getLong("notificationID", 0L)

        Log.d("TTT", "on creating notification")
        val notifyIntent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent
            .getActivity(
                applicationContext,
                0,
                notifyIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        val notificationBuilder = Builder(applicationContext, "CHANNEL_ID")
            .setContentTitle(topic)
            .setStyle(BigTextStyle().bigText(title))
            .setContentText(title)
            .setVisibility(VISIBILITY_PUBLIC)
            .setCategory(CATEGORY_ALARM)
            .setSmallIcon(R.mipmap.custom_launcher_round)
            .setPriority(PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        NotificationManagerCompat.from(applicationContext).notify(notificationId.toInt(), notificationBuilder.build())
        Log.d("TTT", "Notified notification!")

        return Result.success()
    }
}