package com.example.api_retro.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api_retro.R // Asegúrate de tener un recurso drawable o usar íconos
import com.example.api_retro.utils.Constants.Companion.CUSTOM_BLACK
import kotlinx.coroutines.delay

@Composable
fun WelcomeView(navController: NavController) {
    // 1. Efecto de lanzamiento para la navegación automática
    // 1. Efecto de lanzamiento CORREGIDO
    LaunchedEffect(key1 = true) {
        delay(2000L)
        // Navegamos a HomeView
        navController.navigate("HomeView") {
            // Y al mismo tiempo, eliminamos "WelcomeView" de la historia
            // para que si el usuario da "Atrás", no vuelva a la pantalla de carga.
            popUpTo("WelcomeView") {
                inclusive = true
            }
        }
    }

    // 2. Diseño de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(CUSTOM_BLACK))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Aquí puedes usar tu ícono de app o un logo.
        // Nota: Asumo que tienes un recurso de ícono llamado ic_launcher_foreground.
        Image(
            painter = painterResource(id = R.drawable.music),
            contentDescription = "Logo",
            modifier = Modifier.height(150.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Music App",
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Explora el mundo del metal y rock",
            fontSize = 18.sp,
            color = Color.LightGray
        )
    }
}