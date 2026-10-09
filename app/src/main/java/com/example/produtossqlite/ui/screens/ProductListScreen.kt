package com.example.produtossqlite.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.produtossqlite.model.Product
import com.example.produtossqlite.ui.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    products: List<Product>,
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onEditClick: (Product) -> Unit,
    onDeleteClick: (Product) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Lista de Produtos")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Button(
                onClick = onAddClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Adicionar Produto")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                label = {
                    Text(text = "Pesquisar produto")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (products.isEmpty()) {
                Text(text = "Nenhum produto encontrado.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(products) { product ->
                        ProductItem(
                            product = product,
                            onEditClick = {
                                onEditClick(product)
                            },
                            onDeleteClick = {
                                onDeleteClick(product)
                            }
                        )
                    }
                }
            }
        }
    }
}
