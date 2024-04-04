package com.example.coroutinestreamflowrxapp.flows

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.coroutinestreamflowrxapp.Greeting
import com.example.coroutinestreamflowrxapp.ui.theme.CoroutineStreamFlowRxApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class StreamsActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // STREAMS
        setContent {
            CoroutineStreamFlowRxApp {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("hey")
                }
            }
//            coldStreamEg()
//            kotlinFlowOperators()
//            flowContextAndExceptionHandling()
//            KotlinSharedFlow()
            StateFlowVsLiveData()
        }
    }
}

fun coldStreamEg() {
    GlobalScope.launch {
        val data: Flow<Int> = producer()
        data.collect {
            Log.d("PriyankaGupta", "collect 1:  $it")
        }// if we comment this, nothing produces, bcoz no consumer// cold nature
    }
    GlobalScope.launch {
        val data: Flow<Int> = producer()
        delay(2500) // no data loss
        data.collect {
            Log.d("PriyankaGupta", "collect 2:  $it")
        }// if we comment this, nothing produces, bcoz no consumer// cold nature
    }
    // multiple consumers can be there for same flow
}

fun producer() = flow<Int> {
    val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    list.forEach {
        delay(1000)
        emit(it) // By default , Flows create a coroutine scope, which manages itself

    }
}