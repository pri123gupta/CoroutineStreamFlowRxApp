package com.example.coroutinestreamflowrxapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LaunchVsWithcontextVsRunblocking() {

//    executeFnLaunch()
//    executeFnWithContext()
      //  executeFnRunBlocking()  --main()
    executeFnRunBlocking()
}
fun executeFnRunBlocking(){

    runBlocking {
        launch {

            Log.d("PriyankaGupta", "start:  ")
            delay(1000)
        }

        Log.d("PriyankaGupta", "end:  ")
    }
    // runBlocking means - coroutine doesnt end until all work of coroutines don't complete
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun executeFnLaunch() {
    Log.d("PriyankaGupta", "Before:  ")

    GlobalScope.launch(Dispatchers.IO) { // NON- BLOCKING
        delay(1000)
        Log.d("PriyankaGupta", "Inside:  ")
    }
    Log.d("PriyankaGupta", "After:  ")
    /* output:
    PriyankaGupta           Before:
    PriyankaGupta           After:
    PriyankaGupta           Inside:
    */
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun executeFnWithContext() {
    GlobalScope.launch(Dispatchers.IO) {

        Log.d("PriyankaGupta", "Before:  ")
        withContext(Dispatchers.IO) { //BLOCKING- NATURE(blocks thread until this completes)
            delay(1000)
            Log.d("PriyankaGupta", "Inside:  ")
        }
        Log.d("PriyankaGupta", "After:  ")
    }
/*OUTPUT -
  PriyankaGupta            Before:
    PriyankaGupta           Inside:
    PriyankaGupta           After:*/
}
