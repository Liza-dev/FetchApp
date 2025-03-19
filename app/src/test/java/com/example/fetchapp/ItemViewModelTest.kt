package com.example.fetchapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fetchapp.model.Item
import com.example.fetchapp.repository.ItemRepository
import com.example.fetchapp.viewmodel.ItemViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class ItemViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()


    @Mock
    private lateinit var repository: ItemRepository

    private lateinit var viewModel: ItemViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = ItemViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun testFetchItem() = runTest {
        val mockItems = listOf(
            Item("1", 1, "Item A"),
            Item("2", 2, "Item B"),
            Item("3", 1, "Item C"),
            Item("4", 2, null),
            Item("5", 1, ""),
            Item("6", 3, "Item D")
        )
        val expectedItems = listOf(
            Item("1", 1, "Item A"),
            Item("3", 1, "Item C"),
            Item("2", 2, "Item B"),
            Item("6", 3, "Item D")
        )
        `when`(repository.getItems()).thenReturn(Response.success(mockItems))
        viewModel.fetchItems()
        Assert.assertEquals(expectedItems, viewModel.items.value)
        assertFalse(viewModel.loading.value!!)
        assertNull(viewModel.errorMessage.value)
    }

    @Test
    fun fetchItemsErrorMessageTest() = runTest {
        val exceptionMessage = "Network error"
        `when`(repository.getItems()).thenThrow(RuntimeException(exceptionMessage))
        viewModel.fetchItems()
        assertNull(viewModel.items.value)
        assertFalse(viewModel.loading.value!!)
        assertNotNull(viewModel.errorMessage.value)
        assertTrue(viewModel.errorMessage.value!!.contains("Exception: $exceptionMessage"))
    }
}