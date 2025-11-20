package com.dam.mvvm_basic

import android.util.Log
import androidx.compose.ui.graphics.Color

/**
 * Clase para almacenar los datos del juego
 */
object Datos {
    var numero = 0
}

/**
 * Colores utilizados
 * color: Color color normal
 * color_suave: Color color suave para el parpadeo, por defecto Transparente
 * txt: String nombre del color
 */
enum class Colores(val color: Color, val color_suave: Color = Color.Transparent, val txt: String, val function: () -> Unit    ) {
    CLASE_ROJO(color = Color.Red, txt = "roxo", function = { Log.d("miDebug", "Pulsado ROJO") }),
    CLASE_VERDE(color = Color.Green, txt = "verde", function = { Log.d("miDebug", "Pulsado Verde") }),
    CLASE_AZUL(color = Color.Blue, txt = "azul", function = { Log.d("miDebug", "Pulsado Azurl") }),
    CLASE_AMARILLO(color = Color.Yellow, txt = "melo", function = { Log.d("miDebug", "Pulsado Amarillo") }),
    CLASE_START(color = Color.Magenta, color_suave = Color.Red, txt = "Start", function = { Log.d("miDebug", "Pulsado Start") })
}

/**
 * Estados del juego
 * INICIO: estado inicial
 * GENERANDO: generando numero random
 * ADIVINANDO: adivinando el numero
 * @param start_activo: Boolean si el boton Start esta activo
 * @param boton_activo: Boolean si los botones de colores estan activos
 */
enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false),
    GENERANDO(start_activo = false, boton_activo = false),
    ADIVINANDO(start_activo = false, boton_activo = true),
    ERROR(start_activo = true, boton_activo = false), // nuevo estado, salta al fallar 3 veces
}

/**
 * Estados auxiliares para corutinas en el ViewModel
 * @param txt: String nombre del estado
 */
enum class EstadosAuxiliares(val txt: String, val segundo: Int) {
    AUX1(txt = "Cuenta atrás: 1", segundo = 1), // estados que ahora simularán la cuenta atrás
    AUX2(txt = "Cuenta atrás: 2", segundo = 2),
    AUX3(txt = "Cuenta atrás: 3", segundo = 3),
    AUX4(txt = "Cuenta atrás: 4", segundo = 4),
    AUX5(txt = "Cuenta atrás: 5", segundo = 5),
}

