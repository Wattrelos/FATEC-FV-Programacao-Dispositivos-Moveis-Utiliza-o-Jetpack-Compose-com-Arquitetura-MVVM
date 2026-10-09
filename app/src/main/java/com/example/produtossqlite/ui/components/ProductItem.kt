package com.example.produtossqlite.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.produtossqlite.model.Product
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductItem(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Preço: ${currencyFormat.format(product.price)}")
            Text(text = "Quantidade: ${product.quantity}")

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onEditClick
                ) {
                    Text(text = "Editar")
                }

                OutlinedButton(
                    onClick = onDeleteClick
                ) {
                    Text(text = "Excluir")
                }
            }
        }
    }
}