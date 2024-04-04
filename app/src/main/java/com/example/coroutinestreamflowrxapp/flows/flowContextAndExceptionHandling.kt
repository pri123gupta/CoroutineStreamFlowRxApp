package com.example.coroutinestreamflowrxapp.flows
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun flowContextAndExceptionHandling() {
// The context on which collect() method is called, on the same context producer will be called
    // but if there is a requirement , to call api on another coroutine (IO) , and ui updation on main thread,
    // so we need to switch thread , which is done by "withContext()"
    // We get exception, because flow preserves its context. Flow assumes that the context on which it is emitted, on the same it is collected. So we have to use “flowOn()” operator.
    // To tell flow, that its context is to be changed, flowOn() is used, instead on withContext()

    // All the functions before flowOn(), 
    Log.d(TAG, "flowContextAndExceptionHandling :")
    GlobalScope.launch(Dispatchers.Main) {
        producer3()
            .map {
                it * 2
                Log.d(TAG, "map thread : ${Thread.currentThread().name}")
            }
            .filter {
                Log.d(TAG, "filter thread : ${Thread.currentThread().name}")
                it < 8
            }
            .flowOn(Dispatchers.IO)
            .collect {
                Log.d(TAG, "Collector thread : ${Thread.currentThread().name}")
            }
    }
}


fun producer3(): Flow<Int> = flow<Int> {
//    withContext(Dispatchers.IO) {      //crash
    var list = listOf(1, 2, 3, 4, 5)
    list.forEach {
        delay(1000)
        Log.d(TAG, "--------------------Producer thread : ${Thread.currentThread().name}")
        emit(it)
    }
//    }
}