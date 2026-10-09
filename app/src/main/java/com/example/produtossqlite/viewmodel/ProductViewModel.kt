package com.example.produtossqlite.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.produtossqlite.data.repository.ProductRepository
import com.example.produtossqlite.model.Product

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    var products by mutableStateOf(repository.getAllProducts())
        private set

    var searchQuery by mutableStateOf("")
        private set

    var editingProduct by mutableStateOf<Product?>(null)
        private set

    var currentScreen by mutableStateOf("list")
        private set

    val filteredProducts: List<Product>
        get() = products.filter { product ->
            product.name.contains(searchQuery, ignoreCase = true)
        }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun openAddScreen() {
        editingProduct = null
        currentScreen = "form"
    }

    fun openEditScreen(product: Product) {
        editingProduct = product
        currentScreen = "form"
    }

    fun backToList() {
        currentScreen = "list"
    }

    fun saveProduct(product: Product) {
        if (product.id == 0) {
            repository.insertProduct(product)
        } else {
            repository.updateProduct(product)
        }

        refreshProducts()
        currentScreen = "list"
    }

    fun deleteProduct(product: Product) {
        repository.deleteProduct(product.id)
        refreshProducts()
    }

    private fun refreshProducts() {
        products = repository.getAllProducts()
    }
}