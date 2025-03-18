package com.example.fetchapp.repository

import com.example.fetchapp.model.Item
import com.example.fetchapp.network.RetrofitClient
import retrofit2.Response
import javax.inject.Inject

class ItemRepository @Inject constructor(private val retrofitClient: RetrofitClient) {

    suspend fun getItems(): Response<List<Item>> {
        return retrofitClient.getApiService().getItems()
    }
}