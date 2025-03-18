package com.example.fetchapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchapp.model.Item
import com.example.fetchapp.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> = _items

    private val _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun fetchItems() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getItems()
                if (response.isSuccessful) {
                    val filteredItems = response.body()
                        ?.filter { !it.name.isNullOrBlank() }
                        ?.sortedWith(compareBy({ it.listId }, { it.name }))
                    _items.value = filteredItems ?: emptyList()
                    _loading.value = false
                } else {
                    _errorMessage.value = "Error: ${response.code()} ${response.message()}"
                    _loading.value = false
                }
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.message}"
                _loading.value = false
            }
        }
    }
}
