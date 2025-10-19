package com.nicolle.juegocolores

/**
 * Objeto Singleton que gestiona el historial de puntajes de la sesión actual
 * Mantiene una lista en memoria de todos los puntajes obtenidos
 * Esta lista se pierde al cerrar la aplicación (sin persistencia)
 */
object HistorialManager {

    // Lista mutable que almacena todos los puntajes de la sesión
    // Se utiliza una lista porque permite duplicados y mantiene el orden
    private val listaPuntajes = mutableListOf<Int>()

    /**
     * Agrega un nuevo puntaje al historial
     * @param puntaje El puntaje obtenido en la partida
     */
    fun agregarPuntaje(puntaje: Int) {
        listaPuntajes.add(puntaje)
    }

    /**
     * Obtiene todos los puntajes del historial
     * @return Lista inmutable de puntajes
     */
    fun obtenerHistorial(): List<Int> {
        return listaPuntajes.toList()
    }

    /**
     * Obtiene el puntaje más alto de la sesión actual
     * @return El puntaje máximo o 0 si no hay puntajes
     */
    fun obtenerPuntajeMaximo(): Int {
        return listaPuntajes.maxOrNull() ?: 0
    }

    /**
     * Limpia todo el historial
     * Útil si se quiere implementar un botón de "Reset"
     */
    fun limpiarHistorial() {
        listaPuntajes.clear()
    }

    /**
     * Obtiene la cantidad de partidas jugadas en la sesión
     * @return Número de partidas
     */
    fun obtenerCantidadPartidas(): Int {
        return listaPuntajes.size
    }
}