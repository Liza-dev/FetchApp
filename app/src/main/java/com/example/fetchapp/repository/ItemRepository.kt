package com.example.fetchapp.repository

import com.example.fetchapp.model.Item
import com.example.fetchapp.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class ItemRepository @Inject constructor(private val retrofitClient: RetrofitClient) {
    suspend fun getItems(): Response<List<Item>> {
        return withContext(Dispatchers.IO) {
            retrofitClient.getApiService().getItems()
        }
    }
}