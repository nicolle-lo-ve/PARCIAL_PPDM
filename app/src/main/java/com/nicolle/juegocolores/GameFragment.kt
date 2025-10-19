package com.nicolle.juegocolores

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * Fragment principal del juego donde se desarrolla la lógica
 * Maneja el temporizador, la generación de colores aleatorios y el puntaje
 */
class GameFragment : Fragment() {

    // Referencias a las vistas del layout
    private lateinit var viewColorObjetivo: View
    private lateinit var tvPuntaje: TextView
    private lateinit var tvTiempo: TextView
    private lateinit var tvFeedback: TextView
    private lateinit var btnRojo: Button
    private lateinit var btnVerde: Button
    private lateinit var btnAzul: Button
    private lateinit var btnAmarillo: Button

    // Variables del juego
    private var puntaje = 0
    private var colorObjetivoActual = 0
    private var tiempoRestante = 30
    private var temporizador: CountDownTimer? = null

    // Constantes de colores del juego
    // Cada color tiene un identificador único y su valor RGB correspondiente
    private val coloresDelJuego = mapOf(
        COLOR_ROJO to R.color.color_rojo,
        COLOR_VERDE to R.color.color_verde,
        COLOR_AZUL to R.color.color_azul,
        COLOR_AMARILLO to R.color.color_amarillo
    )

    companion object {
        // Identificadores de colores
        const val COLOR_ROJO = 1
        const val COLOR_VERDE = 2
        const val COLOR_AZUL = 3
        const val COLOR_AMARILLO = 4

        // Duración del temporizador en milisegundos (30 segundos)
        const val DURACION_JUEGO = 30000L

        // Intervalo de actualización del temporizador (cada segundo)
        const val INTERVALO_TICK = 1000L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las referencias a las vistas
        inicializarVistas(view)

        // Configura los listeners de los botones
        configurarBotones()

        // Inicia el juego
        iniciarJuego()
    }

    /**
     * Inicializa todas las referencias a las vistas del layout
     */
    private fun inicializarVistas(view: View) {
        viewColorObjetivo = view.findViewById(R.id.viewColorObjetivo)
        tvPuntaje = view.findViewById(R.id.tvPuntaje)
        tvTiempo = view.findViewById(R.id.tvTiempo)
        tvFeedback = view.findViewById(R.id.tvFeedback)
        btnRojo = view.findViewById(R.id.btnRojo)
        btnVerde = view.findViewById(R.id.btnVerde)
        btnAzul = view.findViewById(R.id.btnAzul)
        btnAmarillo = view.findViewById(R.id.btnAmarillo)
    }

    /**
     * Configura los click listeners para todos los botones de colores
     * Cada botón llama a verificarRespuesta con su identificador de color
     */
    private fun configurarBotones() {
        btnRojo.setOnClickListener { verificarRespuesta(COLOR_ROJO) }
        btnVerde.setOnClickListener { verificarRespuesta(COLOR_VERDE) }
        btnAzul.setOnClickListener { verificarRespuesta(COLOR_AZUL) }
        btnAmarillo.setOnClickListener { verificarRespuesta(COLOR_AMARILLO) }
    }

    /**
     * Inicia el juego configurando el estado inicial y el temporizador
     */
    private fun iniciarJuego() {
        // Resetea el puntaje
        puntaje = 0
        actualizarPuntaje()

        // Genera el primer color aleatorio
        generarNuevoColor()

        // Inicia el temporizador de cuenta regresiva
        iniciarTemporizador()
    }

    /**
     * Crea e inicia el CountDownTimer de 30 segundos
     * Se actualiza cada segundo (1000ms)
     */
    private fun iniciarTemporizador() {
        temporizador = object : CountDownTimer(DURACION_JUEGO, INTERVALO_TICK) {
            /**
             * Se ejecuta cada segundo
             * Actualiza el tiempo restante en la UI
             */
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = (millisUntilFinished / 1000).toInt()
                actualizarTiempo()
            }

            /**
             * Se ejecuta cuando el tiempo llega a cero
             * Finaliza el juego y navega a la pantalla de resultados
             */
            override fun onFinish() {
                finalizarJuego()
            }
        }.start()
    }

    /**
     * Genera un color aleatorio y lo muestra en la vista objetivo
     * Utiliza Random para seleccionar uno de los 4 colores disponibles
     */
    private fun generarNuevoColor() {
        // Obtiene un color aleatorio del mapa de colores
        val coloresDisponibles = coloresDelJuego.keys.toList()
        colorObjetivoActual = coloresDisponibles.random()

        // Obtiene el recurso de color correspondiente
        val colorResourceId = coloresDelJuego[colorObjetivoActual] ?: R.color.white
        val color = ContextCompat.getColor(requireContext(), colorResourceId)

        // Aplica el color a la vista con una animación suave
        viewColorObjetivo.setBackgroundColor(color)

        // Anima la vista para dar feedback visual
        animarVistaColor()
    }

    /**
     * Anima la vista del color objetivo con un efecto de escala
     * Da feedback visual cada vez que cambia el color
     */
    private fun animarVistaColor() {
        // Crea una animación de escala (agranda y reduce la vista)
        val animacion = ObjectAnimator.ofFloat(viewColorObjetivo, "scaleX", 0.8f, 1.0f)
        animacion.duration = 300 // Duración en milisegundos

        // Anima también el eje Y para mantener proporción
        val animacionY = ObjectAnimator.ofFloat(viewColorObjetivo, "scaleY", 0.8f, 1.0f)
        animacionY.duration = 300

        animacion.start()
        animacionY.start()
    }

    /**
     * Verifica si la respuesta del usuario es correcta
     * @param colorSeleccionado El identificador del color que el usuario presionó
     */
    private fun verificarRespuesta(colorSeleccionado: Int) {
        if (colorSeleccionado == colorObjetivoActual) {
            // Respuesta correcta
            manejarRespuestaCorrecta()
        } else {
            // Respuesta incorrecta
            manejarRespuestaIncorrecta()
        }
    }

    /**
     * Maneja la lógica cuando el usuario acierta
     * Incrementa el puntaje y genera un nuevo color
     */
    private fun manejarRespuestaCorrecta() {
        // Incrementa el puntaje
        puntaje++
        actualizarPuntaje()

        // Muestra feedback positivo
        mostrarFeedback(true)

        // Genera un nuevo color para la siguiente ronda
        generarNuevoColor()
    }

    /**
     * Maneja la lógica cuando el usuario se equivoca
     * Muestra feedback negativo pero no penaliza el puntaje
     */
    private fun manejarRespuestaIncorrecta() {
        // Muestra feedback negativo
        mostrarFeedback(false)

        // Nota: No generamos nuevo color, el usuario debe intentar de nuevo
        // Esto hace el juego más desafiante
    }

    /**
     * Muestra un mensaje de feedback temporal (Correcto/Incorrecto)
     * @param esCorrecto true si la respuesta fue correcta, false si no
     */
    private fun mostrarFeedback(esCorrecto: Boolean) {
        // Configura el texto y color según el resultado
        if (esCorrecto) {
            tvFeedback.text = getString(R.string.correcto)
            tvFeedback.setTextColor(ContextCompat.getColor(requireContext(), R.color.exito))
        } else {
            tvFeedback.text = getString(R.string.incorrecto)
            tvFeedback.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
        }

        // Hace visible el TextView de feedback
        tvFeedback.visibility = View.VISIBLE

        // Anima el feedback con un efecto de aparición
        tvFeedback.alpha = 0f
        tvFeedback.animate()
            .alpha(1f)
            .setDuration(200)
            .start()

        // Oculta el feedback después de 800ms
        tvFeedback.postDelayed({
            tvFeedback.animate()
                .alpha(0f)
                .setDuration(200)
                .withEndAction {
                    tvFeedback.visibility = View.INVISIBLE
                }
                .start()
        }, 800)
    }

    /**
     * Actualiza el TextView del puntaje con el valor actual
     */
    private fun actualizarPuntaje() {
        tvPuntaje.text = getString(R.string.texto_puntaje, puntaje)
    }

    /**
     * Actualiza el TextView del tiempo restante
     */
    private fun actualizarTiempo() {
        tvTiempo.text = getString(R.string.texto_tiempo, tiempoRestante)

        // Cambia el color del tiempo si quedan menos de 10 segundos
        if (tiempoRestante <= 10) {
            tvTiempo.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))
        }
    }

    /**
     * Finaliza el juego y navega a la pantalla de resultados
     * Cancela el temporizador y pasa el puntaje final como argumento
     */
    private fun finalizarJuego() {
        // Cancela el temporizador si aún está activo
        temporizador?.cancel()

        // Guarda el puntaje en el historial de la sesión
        HistorialManager.agregarPuntaje(puntaje)

        // Navega al ResultFragment pasando el puntaje como argumento
        val action = GameFragmentDirections
            .actionGameFragmentToResultFragment(puntaje)
        findNavController().navigate(action)
    }

    /**
     * Se llama cuando el fragment se destruye
     * Importante: Cancela el temporizador para evitar memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Cancela el temporizador para liberar recursos
        temporizador?.cancel()
        temporizador = null
    }
}