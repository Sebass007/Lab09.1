package com.example.lab09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.lab09.ui.theme.Lab09Theme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            Lab09Theme {
                ProgPrincipal9()
            }
        }
    }
}

@Composable
fun ProgPrincipal9() {

    val urlBase = "https://dummyjson.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(urlBase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val servicioProducts = retrofit.create(ProductApiService::class.java)

    val navController = rememberNavController()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController) },
        content = { paddingValues ->

            Contenido(
                paddingValues,
                navController,
                servicioProducts
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "LABORATORIO 09",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BarraInferior(navController: NavHostController) {

    NavigationBar(
        containerColor = Color.LightGray
    ) {

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = "Inicio"
                )
            },
            label = {
                Text("Inicio")
            },
            selected = navController.currentDestination?.route == "inicio",
            onClick = {
                navController.navigate("inicio")
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Outlined.ShoppingCart,
                    contentDescription = "Productos"
                )
            },
            label = {
                Text("Productos")
            },
            selected = navController.currentDestination?.route == "products",
            onClick = {
                navController.navigate("products")
            }
        )

        NavigationBarItem(
            icon = {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = "Acerca"
                )
            },
            label = {
                Text("Acerca")
            },
            selected = navController.currentDestination?.route == "acerca",
            onClick = {
                navController.navigate("acerca")
            }
        )
    }
}

@Composable
fun Contenido(
    pv: PaddingValues,
    navController: NavHostController,
    servicioProducts: ProductApiService
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(pv)
    ) {

        NavHost(
            navController = navController,
            startDestination = "inicio"
        ) {

            composable("inicio") {
                ScreenInicio()
            }

            composable("products") {

                ScreenProducts(
                    navController,
                    servicioProducts
                )
            }

            composable("acerca") {
                ScreenAcerca()
            }

            composable(
                "producto/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) {

                ScreenProduct(
                    servicioProducts,
                    it.arguments!!.getInt("id")
                )
            }
        }
    }
}

@Composable
fun ScreenInicio() {

    Text("BIENVENIDO AL LABORATORIO 09")
}

@Composable
fun ScreenAcerca() {

    Text("Aplicación desarrollada con Retrofit y Jetpack Compose")
}