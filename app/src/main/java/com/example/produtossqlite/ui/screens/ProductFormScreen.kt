package com.example.produtossqlite.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.produtossqlite.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormScreen(
    productToEdit: Product?,
    onSave: (Product) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(productToEdit?.name ?: "") }
    var price by remember { mutableStateOf(productToEdit?.price?.toString() ?: "") }
    var quantity by remember { mutableStateOf(productToEdit?.quantity?.toString() ?: "") }

    var showError by remember { mutableStateOf(false) }

    val screenTitle = if (productToEdit == null) {
        "Adicionar Produto"
    } else {
        "Editar Produto"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(screenTitle)
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
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text("Nome do produto")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = price,
                onValueChange = {
                    price = it
                },
                label = {
                    Text("Preço")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = quantity,
                onValueChange = {
                    quantity = it
                },
                label = {
                    Text("Quantidade")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val priceValue = price.replace(",", ".").toDoubleOrNull()
                    val quantityValue = quantity.toIntOrNull()

                    if (
                        name.isBlank() ||
                        priceValue == null ||
                        quantityValue == null
                    ) {
                        showError = true
                    } else {
                        val product = Product(
                            id = productToEdit?.id ?: 0,
                            name = name,
                            price = priceValue,
                            quantity = quantityValue
                        )
                        onSave(product)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }

        if (showError) {
            AlertDialog(
                onDismissRequest = {
                    showError = false
                },
                title = {
                    Text("Dados inválidos")
                },
                text = {
                    Text("Preencha o nome, o preço e a quantidade corretamente.")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showError = false
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
