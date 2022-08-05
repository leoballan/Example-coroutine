package com.vila.testcoroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.vila.testcoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlin.random.Random
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class MainActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        init()
    }

    private fun init() {

        val randomNumbre = Random(100)
        
        val time = measureTimeMillis {
            lifecycleScope.launch {
                Log.d("mcontrol", "Coroutina ..... ${Thread.currentThread().name}")
                Log.d("mcontrol", "Start the serial coroutines")

                firstJob(Dispatchers.Default)
                secondJob(Dispatchers.Default)
                Log.d("mcontrol", "end the serial coroutines")
            }
        }
        val time2 = measureTimeMillis {
            lifecycleScope.launch {
                Log.d("mcontrol", "Start the parallel coroutines")
                parallelJob(Dispatchers.IO)
                Log.d("mcontrol", "end the Parallel coroutines")
            }
        }
        Log.d("mcontrol", "serial coroutine spent $time")
        Log.d("mcontrol", "parallel coroutine spent $time2")
    }


    suspend fun firstJob(dispatcher:CoroutineDispatcher){
        withContext(dispatcher) {
            Log.d("mcontrol", "Coroutina ..... ${Thread.currentThread().name}")
            for (count in 0..5) {
                Log.d("mcontrol", "firstJob ..... ${count}")
                delay(1000)
            }
        }
    }

    suspend fun secondJob(dispatcher:CoroutineDispatcher) {
        withContext(dispatcher) {
            Log.d("mcontrol", "Coroutina ..... ${Thread.currentThread().name}")
            for (count in 0..5){
                Log.d("mcontrol", "secondJob ..... ${count}")
                delay(1000)
            }
        }
    }

    suspend fun parallelJob(dispatcher:CoroutineDispatcher){
        coroutineScope {
            val joba = async { firstJob(dispatcher) }
            val jobB = async{secondJob(dispatcher)}
            awaitAll(joba,jobB)
        }
    }

}