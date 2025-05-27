package ch.hslu.mobpro.workerdemo

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import androidx.work.impl.utils.futures.SettableFuture
import com.google.common.util.concurrent.ListenableFuture

class CallbackWorker(
    context: Context,
    params: WorkerParameters
) : ListenableWorker(context, params) {

    @SuppressLint("RestrictedApi")
    override fun startWork(): ListenableFuture<Result> {
        val future = SettableFuture.create<Result>()

        simulateAsyncTask(object : TaskCallback {
            override fun onSuccess() {
                Log.d("CallbackWorker", "Task completed successfully.")
                future.set(Result.success())
            }

            override fun onError(e: Exception) {
                Log.e("CallbackWorker", "Task failed.", e)
                future.set(Result.failure())
            }
        })

        return future
    }

    private fun simulateAsyncTask(callback: TaskCallback) {
        Thread {
            try {
                Thread.sleep(10_000) // Simulate async delay
                callback.onSuccess()
            } catch (e: Exception) {
                callback.onError(e)
            }
        }.start()
    }

    interface TaskCallback {
        fun onSuccess()
        fun onError(e: Exception)
    }
}
