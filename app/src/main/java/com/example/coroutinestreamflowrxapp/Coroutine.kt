package com.example.coroutinestreamflowrxapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CoroutineBuilders() {
    CoroutineScope(Dispatchers.Main).launch {
        /* task1()
         task2()*/

//         asyncVsLaunch()
//         printFbFollowers()
//        printFbFollowers2()
//        printFbFollowersAsync()

        //- when output is needed after completion of both task
//        printFbInstaFollowersConcurrentlyLaunch() // output prints only when both jobs complete

        //- when output is needed after completion of both task , WITH RESULT
        printFbInstaFollowersConcurrentlyAsync() //output prints only when both job complete but code is simplified since return output is required.

        //now run parallel functions , when they r not dependent

        //- when output is needed after completion of both task ,
        // WITH RESULT, AND BOTH TASKS ARE INDEPENDENT AND CAN BE RUN IN PARALLEL (TO REDUCE TIME TAKEN)
        printParallelIndependentFuncs()
    }
}

suspend fun printParallelIndependentFuncs() {
    //output prints only when both job complete but code is simplified since return output is required.
// and takes lesser time, since both jobs state in parallel
    CoroutineScope(Dispatchers.IO).launch {
        val job1 = async { getFbFollowers() }
        val job2 = async { getInstaFollowers() }

        Log.d("PriyankaGupta", "FB:  ${job1.await()} , insta- ${job2.await()}")
    }
}

suspend fun printFbFollowers() {
    var followers = 0
    CoroutineScope(Dispatchers.IO).launch {
        followers = getFbFollowers()
    }// coroutine is not completed but log prints
    Log.d("PriyankaGupta", "printFbFollowers:  $followers")
}

suspend fun printFbFollowers2() {
    var followers = 0
    val job = CoroutineScope(Dispatchers.IO).launch {
        followers = getFbFollowers()
    }
    job.join() // when coroutine completes, then only next lines execute
    // job type is Job
    Log.d("PriyankaGupta", "printFbFollowers:  $followers")
}

suspend fun printFbFollowersAsync() {
    var followers = 0
    val job = CoroutineScope(Dispatchers.IO).async {
        followers = getFbFollowers()
        "HEllo"
    }  // when coroutine completes, then only next lines execute
    // Job type is differed i.e. returns last statement as return type
    Log.d("PriyankaGupta", "printFbFollowers: $followers , ${job.await().toString()}")
    Log.d("PriyankaGupta", "printFbFollowers: $followers ")
}

suspend fun printFbInstaFollowersConcurrentlyLaunch() {
    var fBfollowers = 0
    var instaFollowrs = 0
    val job = CoroutineScope(Dispatchers.IO).launch {
        fBfollowers = getFbFollowers()
    }
    val job2 = CoroutineScope(Dispatchers.IO).launch {
        instaFollowrs = getInstaFollowers()
    }
    job.join()
    job2.join()
    // we want output prints only when both jobs r complete
    Log.d("PriyankaGupta", " FbFollowers:  $fBfollowers  , instaFollowers $instaFollowrs")
}

suspend fun printFbInstaFollowersConcurrentlyAsync() {

    // we want output prints only when both jobs r complete
    // but code is simplified since return output is required.
    val job = CoroutineScope(Dispatchers.IO).async {
        getFbFollowers()
    }
    val job2 = CoroutineScope(Dispatchers.IO).async {
        getInstaFollowers()
    }
    // we want output prints only when both jobs r complete
    Log.d("PriyankaGupta", " FbFollowers:  ${job.await()}  , instaFollowers ${job2.await()}")
}

suspend fun getInstaFollowers(): Int {
    delay(1000)
    return 112
}

suspend fun getFbFollowers(): Int {
    delay(1000)
    return 54
}

suspend fun asyncVsLaunch() {
    val job = CoroutineScope(Dispatchers.Main).launch {

    }
    job.cancel()
    job.join()  // makes the thread suspended, not blocked.
}

suspend fun task1() {
    Log.d("PriyankaGupta", "task1: start")
    yield() // or delay()
    Log.d("PriyankaGupta", "task1: end ")
}

suspend fun task2() {
    Log.d("PriyankaGupta", "task2: start")
    yield()
    Log.d("PriyankaGupta", "task2: end ")
}