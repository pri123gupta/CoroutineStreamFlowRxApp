package com.example.coroutinestreamflowrxapp.flows

import android.util.Log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

val TAG = "PriyankaGupta"
fun kotlinFlowOperators() {
//    flowFunctions()
//    flowOperators()
//    flowOperatorsEg()
//    flowOperatorsBufferEg()
}

fun flowOperatorsBufferEg() {
    // production is fast (1s), consumption is late(1.5s), so we add buffering
    GlobalScope.launch {
        val time = measureTimeMillis {
            producer2()
                .buffer(5)            // to make code fast
                .collect {
                    delay(1500)
                    Log.d(TAG, "flowOperatorsBufferEg: $it")
                }
        }
        Log.d(TAG, "flowOperatorsBufferEg: time = $time")
    }
    /*   time without buffer() => time = 12840
         time with buffer() => time = 8691
    * */
}

fun flowOperatorsEg() {
    GlobalScope.launch {
// we want title in uppercase, only active items
        getNotes()
            .map { FormattedNote(it.isActive, it.title.uppercase(), it.desc) }
            .filter { it.isActive }
            .collect {
                Log.d(TAG, "flowOperatorsEg: $it ")
            }
    }
}

fun getNotes() = listOf(
    Note(101, true, "First", "First Description"),
    Note(102, true, "Second", "Second Description"),
    Note(103, true, "Third", "Third Description"),
    Note(104, false, "Fourth", "Fourth Description")
).asFlow()               // to convert list to flow , we have a builder asFlow

data class Note(val id: Int, val isActive: Boolean, val title: String, val desc: String)
data class FormattedNote(val isActive: Boolean, val title: String, val desc: String)

fun flowOperators() {
    GlobalScope.launch {
        /*    val first =   producer2().first()  // takes first element and return
              Log.d(MyTAG, "first: $first ")
              val list  =   producer2().toList()  // takes all  element, make list and return
              Log.d(MyTAG, "toList: $list  ")*/

        producer2()
            .map {                                                // Non - Terminal operators
                it * 2
            }
            .filter {                                                // Non - Terminal operators
                it < 8
            }
            .collect {                                               //  Terminal operators to start flow
                Log.d("PriyankaGupta", "collected : $it ")
            }
    }
}

fun flowFunctions() {
    GlobalScope.launch {
        producer2()
            .onStart {
                emit(-1)   // We can manually emit in onStart, onCompletion methods

                Log.d("PriyankaGupta", "starting :  ")
            }
            .onCompletion {
                emit(6)   //We can manually emit in onStart, onCompletion methods
                Log.d("PriyankaGupta", "Completed :  ")
            }
            .onEach {
                Log.d("PriyankaGupta", "About to emit : $it ")
            }
            .collect {                                                      // Terminal operators
                Log.d("PriyankaGupta", "collected : $it ")
            }

        //We can manually emit in onStart, onCompletion methods
        // Use case - Eg.  loader starts when flow start,  then api call to get data, on completion the loader is dismissed & cleanup work
    }
}

fun producer2(): Flow<Int> = flow<Int> {
    var list = listOf(1, 2, 3, 4, 5)
    list.forEach {
        delay(1000)
        emit(it)
    }
}