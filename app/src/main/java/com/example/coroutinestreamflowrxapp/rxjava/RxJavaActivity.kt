package com.example.coroutinestreamflowrxapp.rxjava

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coroutinestreamflowrxapp.Greeting
import com.example.coroutinestreamflowrxapp.ui.theme.CoroutineStreamFlowRxApp
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class RxJavaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutineStreamFlowRxApp {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
            simpleObserver()   // recommended
//            createObservable()// another method to create observable:  usually we don't use this practice

        }
    }
}

@Composable
fun ClickButton() {
    Card(
        modifier = Modifier
            .width(150.dp)
            .height(64.dp)
//            .mouseClickable { println("Clicked card") }
    ) {
        Column {
            Button(
               onClick =  { println("Clicked button") }

            ) {
                Text("Click me") }
        }
    }
}

fun createObservable() {
    val observable = Observable.create<String> {
        it.onNext("A")
        it.onNext("B")
        it.onError(IllegalArgumentException("Error in Observable"))
        it.onNext("C")
        it.onComplete()
        // on getting error observable stops there, onNext(C) & onComplete are not called
    }
    observable.subscribe(object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            Log.d("PriyankaGupta", "onSubscribe: ") // ready to listen to observable
        }

        override fun onNext(t: String) {
            Log.d("PriyankaGupta", "onNext : $t ")  // when data is emitted, called for every item
        }

        override fun onError(e: Throwable) {

            Log.d("PriyankaGupta", "onError: ${e.message}") // exception
        }

        override fun onComplete() {

            Log.d("PriyankaGupta", "onComplete  ") // all data complete
        }
    })
}

fun simpleObserver() {
    val list = listOf("A", "B", "C")
    val observable = Observable.fromIterable(list)
    observable.subscribe(object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
            Log.d("PriyankaGupta", "onSubscribe: ") // ready to listen to observable
        }

        override fun onNext(t: String) {
            Log.d("PriyankaGupta", "onNext : $t ")  // when data is emitted, called for every item
        }

        override fun onError(e: Throwable) {

            Log.d("PriyankaGupta", "onError: ${e.message}") // exception
        }

        override fun onComplete() {

            Log.d("PriyankaGupta", "onComplete  ") // all data complete
        }


    })
}
