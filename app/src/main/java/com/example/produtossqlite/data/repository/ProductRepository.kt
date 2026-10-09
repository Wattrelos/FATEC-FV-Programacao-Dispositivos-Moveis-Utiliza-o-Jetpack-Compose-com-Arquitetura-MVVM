package com.example.produtossqlite.data.repository

import com.example.produtossqlite.data.local.ProductDbHelper
import com.example.produtossqlite.model.Product

class ProductRepository(
    private val dbHelper: ProductDbHelper
) {
    fun insertProduct(product: Product): Long {
        return dbHelper.insertProduct(product)
    }

    fun getAllProducts(): List<Product> {
        return dbHelper.getAllProducts()
    }

    fun updateProduct(product: Product): Int {
        return dbHelper.updateProduct(product)
    }

    fun deleteProduct(id: Int): Int {
        return dbHelper.deleteProduct(id)
    }
}
