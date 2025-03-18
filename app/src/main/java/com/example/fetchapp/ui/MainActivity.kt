package com.example.fetchapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fetchapp.databinding.ActivityMainBinding
import com.example.fetchapp.viewmodel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.example.fetchapp.ItemAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private val viewModel: ItemViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()
        setupRecyclerView()
        observeViewModel()
        viewModel.fetchItems()

    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter()
        binding.content.recylerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = itemAdapter
        }
    }


    private fun observeViewModel() {
        viewModel.items.observe(this) { items ->
            val groupedItems = items.groupBy { it.listId }
            itemAdapter.updateData(groupedItems)
        }

        viewModel.errorMessage.observe(this) { message ->
            binding.content.emptyTextView.text = message
            binding.content.emptyTextView.visibility = View.VISIBLE
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.content.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}