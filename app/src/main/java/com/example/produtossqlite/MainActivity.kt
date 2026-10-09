package com.example.produtossqlite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.produtossqlite.data.local.ProductDbHelper
import com.example.produtossqlite.data.repository.ProductRepository
import com.example.produtossqlite.ui.screens.ProductFormScreen
import com.example.produtossqlite.ui.screens.ProductListScreen
import com.example.produtossqlite.viewmodel.ProductViewModel
import com.example.produtossqlite.viewmodel.ProductViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = ProductDbHelper(this)
        val repository = ProductRepository(dbHelper)
        val factory = ProductViewModelFactory(repository)

        setContent {
            MaterialTheme {
                val productViewModel: ProductViewModel = viewModel(
                    factory = factory
                )

                ProductApp(viewModel = productViewModel)
            }
        }
    }
}

@Composable
fun ProductApp(viewModel: ProductViewModel) {
    when (viewModel.currentScreen) {
        "list" -> {
            ProductListScreen(
                products = viewModel.filteredProducts,
                searchQuery = viewModel.searchQuery,
                onSearchChange = { query ->
                    viewModel.updateSearchQuery(query)
                },
                onAddClick = {
                    viewModel.openAddScreen()
                },
                onEditClick = { product ->
                    viewModel.openEditScreen(product)
                },
                onDeleteClick = { product ->
                    viewModel.deleteProduct(product)
                }
            )
        }

        "form" -> {
            ProductFormScreen(
                productToEdit = viewModel.editingProduct,
                onSave = { product ->
                    viewModel.saveProduct(product)
                },
                onCancel = {
                    viewModel.backToList()
                }
            )
        }
    }
}
