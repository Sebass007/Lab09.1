package com.example.lab09

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter

@Composable
fun ScreenProducts(
    navController: NavHostController,
    servicio: ProductApiService
) {

    var listaProductos: SnapshotStateList<ProductModel> =
        remember { mutableStateListOf() }

    LaunchedEffect(Unit) {

        val listado = servicio.getProducts()

        listado.products.forEach {
            listaProductos.add(it)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(listaProductos) { item ->

            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("producto/${item.id}")
                    }
            ) {

                Column(
                    modifier = Modifier.padding(12.dp)
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(item.thumbnail),
                        contentDescription = item.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(text = "Marca: ${item.brand}")

                    Text(text = "Precio: $ ${item.price}")

                    Text(text = "Rating: ${item.rating}")
                }
            }
        }
    }
}

@Composable
fun ScreenProduct(
    servicio: ProductApiService,
    id: Int
) {

    var producto by remember {
        mutableStateOf<ProductModel?>(null)
    }

    LaunchedEffect(Unit) {

        producto = servicio.getProductById(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {

        producto?.let {

            Image(
                painter = rememberAsyncImagePainter(it.thumbnail),
                contentDescription = it.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = it.title,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = it.description)

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Categoría: ${it.category}")

            Text(text = "Marca: ${it.brand}")

            Text(text = "Precio: $ ${it.price}")

            Text(text = "Descuento: ${it.discountPercentage}%")

            Text(text = "Stock: ${it.stock}")

            Text(text = "Rating: ${it.rating}")
        }
    }
}