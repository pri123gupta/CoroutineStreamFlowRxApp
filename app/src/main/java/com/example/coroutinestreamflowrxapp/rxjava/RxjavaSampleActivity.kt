package com.example.coroutinestreamflowrxapp.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.coroutinestreamflowrxapp.R
import com.example.coroutinestreamflowrxapp.rxjava.models.Product
import com.example.coroutinestreamflowrxapp.rxjava.network.ProductService
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RxjavaSampleActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rxjava_sample)
        val button = findViewById<Button>(R.id.button2)
        val observable = button
            .clicks()               // returns observable
            .throttleFirst(3000, TimeUnit.MILLISECONDS) // takes next o/p after 3s
            .subscribe {                             // imlementation if onNext()
                Log.d("PriyankaGupta", "onCreate: onNext()")
            }
        implementNetworkCall()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

fun implementNetworkCall() {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val productService = retrofit.create(ProductService::class.java)
    val products = productService.getProducts()

    products
        .subscribeOn(Schedulers.io()) // n/w call on bg thread
        .observeOn(AndroidSchedulers.mainThread()) // response i.e. UI on main thread
        .subscribe {
            it.forEach {
                Log.d("PriyankaGupta", " ${it.description} \n\n")
            }
        }


}