package com.example.coroutinestreamflowrxapp.flows

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.log


fun StateFlowVsLiveData() {
//    sharedFlowWorking()
//    stateFlowAsFlow()
//   stateFlowAsStateFlow()
}

fun stateFlowAsStateFlow() {
    GlobalScope.launch {
        val result = producerStateFlowAsStateFlow()
        Log.d(TAG, "stateFlow.value : $result.value")
        // as soon as stream starts, default value of flow is 0
    }
}

fun stateFlowAsFlow() {
    GlobalScope.launch {
        val result = producerStateFlowAsFlow()
//           delay(2500)
        delay(7000)
        result.collect {       // last value 5 is collected , no matter what the delay is
            Log.d(TAG, "Collecting_A_ $it ")
        }
    }
}

fun sharedFlowWorking() {
    GlobalScope.launch {
        val result = producerSharedFlow()
//        delay(2500)
        delay(7000)            // collect fn not called , bcoz hot stream , all data is lost
        result.collect {
            Log.d(TAG, "Collecting_A = $it ")
        }
    }
}

fun producerSharedFlow(): Flow<Int> {
    val mutaFlow = MutableSharedFlow<Int>()
    val list = listOf(1, 2, 3, 4, 5)
    GlobalScope.launch {
        list.forEach {
            Log.d(TAG, "------------------------------------Emitting: $it ")
            mutaFlow.emit(it)
            delay(1000)
        }
    }
    return mutaFlow
}

fun producerStateFlowAsFlow(): Flow<Int> {
    val mutableStateFlow = MutableStateFlow<Int>(0)
    val list = listOf(1, 2, 3, 4, 5)
    GlobalScope.launch {
        list.forEach {
            Log.d(TAG, "------------------------------------Emitting: $it ")
            mutableStateFlow.emit(it)
            delay(1000)
        }
    }
    return mutableStateFlow
}

fun producerStateFlowAsStateFlow(): StateFlow<Int> {
    val mutableStateFlow = MutableStateFlow<Int>(0)
    val list = listOf(1, 2, 3, 4, 5)
    GlobalScope.launch {
        list.forEach {
            Log.d(TAG, "------------------------------------Emitting: $it ")
            mutableStateFlow.emit(it)
            delay(1000)
        }
    }
    return mutableStateFlow
}