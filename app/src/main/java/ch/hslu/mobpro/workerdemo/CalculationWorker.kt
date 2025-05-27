package ch.hslu.mobpro.workerdemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CalculationWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            "default_channel",
            "Default Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        val result = longRunningOperation()
        val notification = NotificationCompat.Builder(applicationContext, "default_channel")
            .setContentTitle("Calculation")
            .setContentText("Result is: $result")
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .build()

        notificationManager.notify(1, notification)
        Log.d("NotificationWorker", "Showing notification...")
        return Result.success()
    }

    private suspend fun longRunningOperation(): Int = withContext(Dispatchers.Default) {
        val list = mutableListOf<Int>()

        for (i in 0..1_000) {
            list.add(i)
        }
        repeat(20_000) {
            list.shuffle()
            list.sort()
            list.shuffle()
        }

        list.first()
    }
}
