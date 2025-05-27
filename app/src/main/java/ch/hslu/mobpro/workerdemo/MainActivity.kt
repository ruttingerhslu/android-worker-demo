package ch.hslu.mobpro.workerdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ch.hslu.mobpro.workerdemo.ui.theme.WorkerDemoTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current;
            WorkerDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Button(onClick = {
                            val request = OneTimeWorkRequestBuilder<NotificationWorker>()
                                .setInitialDelay(10, TimeUnit.SECONDS)
                                .build()

                            WorkManager.getInstance(context).enqueue(request)
                        }) {
                            Text("Schedule notification")
                        }
                        Button(onClick = {
                            val request = OneTimeWorkRequestBuilder<CalculationWorker>()
                                .setInitialDelay(15, TimeUnit.SECONDS)
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }) {
                            Text("Schedule calculation")
                        }
                        Button(onClick = {
                            val request = OneTimeWorkRequestBuilder<SimpleCoroutineWorker>()
                                .setInitialDelay(15, TimeUnit.SECONDS)
                                .build()
                            WorkManager.getInstance(context).enqueue(
                                request
                            )
                        }) {
                            Text("Schedule simple coroutine")
                        }
                        Button(onClick = {
                            val request = OneTimeWorkRequestBuilder<CallbackWorker>()
                                .build()
                            WorkManager.getInstance(context).enqueue(
                                request
                            )
                        }) {
                            Text("Schedule callback worker")
                        }
                    }
                }
            }
        }
    }
}
