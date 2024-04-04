package com.example.coroutinestreamflowrxapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutinestreamflowrxapp.rxjava.RxjavaSampleActivity
import com.example.coroutinestreamflowrxapp.ui.theme.CoroutineStreamFlowRxApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutineStreamFlowRxApp {
                // A surface container using the 'background' color from the theme
                Surface(
                    Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
//                    CoroutineBuilders()
//                    MyCounter()
                    Greeting("Android")
//                    CoroutineHierarchy()
//                    LaunchVsWithcontextVsRunblocking()
//                    ViewmodelScopeLifecycleScope()
                }
            }
//            startActivity(Intent(this, RxJavaActivity::class.java))
            startActivity(Intent(this, RxjavaSampleActivity::class.java))


        }
    }
}

@Composable  // state full composable
fun MyCounter() {
    // state hoisting
    val count: MutableState<Int> = rememberSaveable { mutableStateOf(0) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(1f)
    ) {
        DisplayText(count.value)
        MyButton(count.value) {
            count.value

        }
    }
}

@Composable // stateless composable
fun DisplayText(count: Int) {
    Text(text = "count =  $count")
}

@Composable // stateless composable
fun MyButton(count: Int, onClick: () -> Unit) {

    Column {
        Text(text = "You have $count in counter ")
        Button(onClick = {
            onClick
        }) {
            Text(text = "Update Counter ")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoroutineStreamFlowRxApp {
        Greeting("Android")
    }
}