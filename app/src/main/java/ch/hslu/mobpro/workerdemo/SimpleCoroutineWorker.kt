package ch.hslu.mobpro.workerdemo

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class SimpleCoroutineWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("SimpleCoroutineWorker", "Work started...")

        repeat(5) { i ->
            delay(1_000L)
            Log.d("SimpleCoroutineWorker", "Step ${i + 1} complete")
        }

        Log.d("SimpleCoroutineWorker", "Work finished.")
        return Result.success()
    }
}
