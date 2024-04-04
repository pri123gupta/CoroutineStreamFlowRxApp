package com.example.coroutinestreamflowrxapp.flows

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun KotlinSharedFlow() {
    // Every consumer gets its individual object
    // but if we want every consumer should not get individual object ,
    // instead a single object , that is shared among them, then we use shared flow .

//    noSharedFlow()
    withSharedFlow()
// with SharedFlow is hot flow, same object is shared between both , but for 2nd consumer , data is lost because of delay

}

fun withSharedFlow() {
    GlobalScope.launch() {
        producerMutableSharedFlow()
            .collect {
                Log.d(TAG, "CollectorA : ${it}")
            }
    }

    GlobalScope.launch() {
        val result = producerMutableSharedFlow()
        delay(2500)
        result.collect {
            Log.d(TAG, "CollectorB : ${it}")
        }
    }
}

fun noSharedFlow() {
    GlobalScope.launch() {
        producer4()
            .collect {
                Log.d(TAG, "Collector1 : ${it}")
            }
    }

    GlobalScope.launch() {
        val result = producer4()
        delay(2500)
        result.collect {
            Log.d(TAG, "Collector2 : ${it}")
        }
    }
}

fun producerMutableSharedFlow(): Flow<Int> {  // returns flow, not mutable , for encapsulation
    val mutableSharedFlow = MutableSharedFlow<Int>(
        replay = 2           // replay the lost values , if a consumer joins late
    //  replay [ =1 means 2nd consumer gets 1 unit before data also, which was lost
    )                 //  (stores last 2 values lost, and the 2nd consumer starts from it )]

    var list = listOf(1, 2, 3, 4, 5)
    GlobalScope.launch {
        list.forEach {
            mutableSharedFlow.emit(it)
            Log.d(TAG, "------------------------------------------   : ${it}")
            delay(1000)
        }
    }
    return mutableSharedFlow

}

fun producer4(): Flow<Int> = flow<Int> {
    var list = listOf(1, 2, 3, 4, 5)
    list.forEach {
        delay(1000)
        Log.d(TAG, "-------------------------Producer KotlinSharedFlow  : ${it}")
        emit(it)
    }
}