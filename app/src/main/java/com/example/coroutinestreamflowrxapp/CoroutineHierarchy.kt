package com.example.coroutinestreamflowrxapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CoroutineHierarchy() {
//   Advantage - If a parent job is canceled, children are automatically canceled.

    /*Job1{        // Parent Job
        Job2{} // child job
        Job3{} // child job
    }*/
    GlobalScope.launch(Dispatchers.IO) {
//        executeFn1()
//        executeFn2()
        executeFn3Delay()
    }
}

suspend fun executeFn1() {
    //-  Child jobs inherit the context of parent job, but possible to  override also
    val jobParent = GlobalScope.launch(Dispatchers.Main) {

        Log.d("PriyankaGupta", "Parent context  :  $coroutineContext")
        val jobChild1 = launch {

            Log.d("PriyankaGupta", "Child 1 context  :  $coroutineContext")
        }
        val jobChild2 =GlobalScope.launch(Dispatchers.IO) {

            Log.d("PriyankaGupta", "Child 2 context  :  $coroutineContext")
        }
    }
}

suspend fun executeFn2() {
    //- parent job waits for child job to complete ,
    val jobParent = GlobalScope.launch(Dispatchers.Main) {

        Log.d("PriyankaGupta", "Parent started ")
        val jobChild1 = launch {
            Log.d("PriyankaGupta", "Child started  ")
            delay(5000)
            Log.d("PriyankaGupta", "Child ended  ")
        }
        delay(3000)
        Log.d("PriyankaGupta", "Parent ended ")
    }
    jobParent.join() // unless parent completes, next code wont run , will be suspended.

    Log.d("PriyankaGupta", "Parent completed ")
}
suspend fun executeFn3Delay() {
    val jobParent = GlobalScope.launch(Dispatchers.Main) {

        Log.d("PriyankaGupta", "Parent started ")
        val jobChild1 = launch {
            Log.d("PriyankaGupta", "Child started  ")
            delay(5000)
            Log.d("PriyankaGupta", "Child ended  ")
        }
        delay(3000)
        Log.d("PriyankaGupta", "Parent ended ")
    }
    delay(1000)
    jobParent.cancel()
    jobParent.join()
    Log.d("PriyankaGupta", "Parent completed ")
}