package com.dam.mvvm_basic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyViewModel(): ViewModel() {

    // etiqueta para logcat
    private val TAG_LOG = "miDebug"

    // estados del juego
    // usamos LiveData para que la IU se actualice
    // patron de diseño observer
    val estadoActual = MutableStateFlow(Estados.INICIO)

    val _fallos = MutableStateFlow(0) // observable de los fallos
    val _progreso = MutableStateFlow(0) // observable del progreso

    // este va a ser nuestra lista para la secuencia random
    // usamos mutable, ya que la queremos modificar
    var _numbers = MutableStateFlow(0)

    // inicializamos variables cuando instanciamos
    init {
        // estado inicial
        Log.d(TAG_LOG, "Inicializamos ViewModel - Estado: ${estadoActual.value}")
    }

    /**
     * crear entero random
     */
    fun crearRandom() {
        viewModelScope.launch {
            estadoActual.value = Estados.GENERANDO

            for (i in 0..100 step 10) { // simulamos progreso con una corutina
                _progreso.value = i
                delay(100)
            }

            // cambiamos estado, por lo tanto la IU se actualiza
            estadoActual.value = Estados.GENERANDO
            _numbers.value = (0..3).random()
            Log.d(TAG_LOG, "creamos random ${_numbers.value} - Estado: ${estadoActual.value}")
            actualizarNumero(_numbers.value)
        }
    }

    fun actualizarNumero(numero: Int) {
        Log.d(TAG_LOG, "actualizamos numero en Datos - Estado: ${estadoActual.value}")
        Datos.numero = numero
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoActual.value = Estados.ADIVINANDO
    }

    /**
     * comprobar si el boton pulsado es el correcto
     * @param ordinal: Int numero de boton pulsado
     * @return Boolean si coincide TRUE, si no FALSE
     */
    fun comprobar(ordinal: Int): Boolean {

        // mientras comprobamos, lanzamos estados auxiliares en paralelo
        estadosAuxiliares()

        Log.d(TAG_LOG, "comprobamos - Estado: ${estadoActual.value}")

         if (ordinal == Datos.numero) {
            _fallos.value = 0 // reseteamos fallos si acierta
            Log.d(TAG_LOG, "es correcto")
            estadoActual.value = Estados.INICIO
            Log.d(TAG_LOG, "GANAMOS - Estado: ${estadoActual.value}")
            return true
        } else {
            _fallos.value ++ // incremento fallos

             if (_fallos.value >= 3) {
                 Log.d(TAG_LOG, "3 fallos, pasamos a estado ERROR")
                 estadoActual.value = Estados.ERROR
                 _fallos.value = 0 // reseteamos fallos al entrar en error
                 return false

             }
            Log.d(TAG_LOG, "no es correcto, fallos: ${_fallos.value}")
            estadoActual.value = Estados.ADIVINANDO
            Log.d(TAG_LOG, "otro intento - Estado: ${estadoActual.value}")
             return false
        }
    }

    /**
     * Corutina que lanza estados auxiliares
     */
    fun estadosAuxiliares() {
        viewModelScope.launch {
            // guardamos el estado auxiliar
            var estadoAux = EstadosAuxiliares.AUX1

            // hacemos un cambio a tres estados auxiliares
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1500)
            estadoAux = EstadosAuxiliares.AUX2
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1500)
            estadoAux = EstadosAuxiliares.AUX3
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1500)
        }
    }

    // funcion para resetear el juego
    fun resetear() {
        Log.d(TAG_LOG, "Resteando el juego, ESTADO - ${estadoActual.value}")
        estadoActual.value = Estados.INICIO
        _fallos.value = 0
        _progreso.value = 0
        Log.d(TAG_LOG, "Juego reiniciado, ESTADO - ${estadoActual.value}")

    }
}