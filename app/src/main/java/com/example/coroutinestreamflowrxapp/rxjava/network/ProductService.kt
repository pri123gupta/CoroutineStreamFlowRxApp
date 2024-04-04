package com.example.coroutinestreamflowrxapp.rxjava.network

import com.example.coroutinestreamflowrxapp.rxjava.models.Product
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ProductService {

    @GET("/products")
    fun getProducts() : Observable<List<Product>>     // Returns Observable for Rxjava
}