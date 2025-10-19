package com.nicolle.juegocolores

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Fragment que muestra los resultados de la partida
 * Incluye el puntaje final, récord histórico y historial de la sesión
 */
class ResultFragment : Fragment() {

    // Recibe los argumentos pasados desde el GameFragment
    private val args: ResultFragmentArgs by navArgs()

    // Referencias a las vistas
    private lateinit var tvPuntajeFinal: TextView
    private lateinit var tvRecordHistorico: TextView
    private lateinit var tvNuevoRecord: TextView
    private lateinit var recyclerViewHistorial: RecyclerView
    private lateinit var btnJugarNuevamente: Button
    private lateinit var btnMenuPrincipal: Button

    // Adapter para el RecyclerView
    private lateinit var historialAdapter: HistorialAdapter

    // SharedPreferences para guardar el récord histórico
    private val PREFS_NAME = "JuegoColoresPrefs"
    private val KEY_RECORD = "record_historico"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa las vistas
        inicializarVistas(view)

        // Configura el RecyclerView
        configurarRecyclerView()

        // Muestra los resultados
        mostrarResultados()

        // Configura los botones
        configurarBotones()
    }

    /**
     * Inicializa todas las referencias a las vistas
     */
    private fun inicializarVistas(view: View) {
        tvPuntajeFinal = view.findViewById(R.id.tvPuntajeFinal)
        tvRecordHistorico = view.findViewById(R.id.tvRecordHistorico)
        tvNuevoRecord = view.findViewById(R.id.tvNuevoRecord)
        recyclerViewHistorial = view.findViewById(R.id.recyclerViewHistorial)
        btnJugarNuevamente = view.findViewById(R.id.btnJugarNuevamente)
        btnMenuPrincipal = view.findViewById(R.id.btnMenuPrincipal)
    }

    /**
     * Configura el RecyclerView con su adapter y layout manager
     */
    private fun configurarRecyclerView() {
        // Crea el adapter con el historial de la sesión
        historialAdapter = HistorialAdapter(HistorialManager.obtenerHistorial())

        // Configura el RecyclerView
        recyclerViewHistorial.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historialAdapter
            setHasFixedSize(true) // Optimización si el tamaño no cambia
        }
    }

    /**
     * Muestra el puntaje final y el récord histórico
     * Actualiza el récord si es necesario
     */
    private fun mostrarResultados() {
        // Obtiene el puntaje final de los argumentos
        val puntajeFinal = args.puntajeFinal

        // Muestra el puntaje final
        tvPuntajeFinal.text = getString(R.string.texto_puntaje_final, puntajeFinal)

        // Obtiene el récord histórico de SharedPreferences
        val sharedPrefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        var recordHistorico = sharedPrefs.getInt(KEY_RECORD, 0)

        // Verifica si hay un nuevo récord
        var esNuevoRecord = false
        if (puntajeFinal > recordHistorico) {
            // Actualiza el récord en SharedPreferences
            recordHistorico = puntajeFinal
            sharedPrefs.edit().putInt(KEY_RECORD, recordHistorico).apply()
            esNuevoRecord = true
        }

        // Muestra el récord histórico
        tvRecordHistorico.text = getString(R.string.texto_record, recordHistorico)

        // Muestra el mensaje de nuevo récord si aplica
        if (esNuevoRecord) {
            tvNuevoRecord.visibility = View.VISIBLE
            // Anima el mensaje de nuevo récord
            animarNuevoRecord()
        } else {
            tvNuevoRecord.visibility = View.GONE
        }
    }

    /**
     * Anima el mensaje de "Nuevo Récord" con un efecto de parpadeo
     */
    private fun animarNuevoRecord() {
        tvNuevoRecord.alpha = 0f
        tvNuevoRecord.animate()
            .alpha(1f)
            .setDuration(500)
            .withEndAction {
                // Efecto de parpadeo
                tvNuevoRecord.animate()
                    .alpha(0.5f)
                    .setDuration(500)
                    .withEndAction {
                        tvNuevoRecord.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .start()
                    }
                    .start()
            }
            .start()
    }

    /**
     * Configura los listeners de los botones
     */
    private fun configurarBotones() {
        // Botón "Jugar Nuevamente" - Navega directamente al GameFragment
        btnJugarNuevamente.setOnClickListener {
            findNavController().navigate(
                R.id.action_resultFragment_to_gameFragment
            )
        }

        // Botón "Menú Principal" - Navega al WelcomeFragment
        btnMenuPrincipal.setOnClickListener {
            findNavController().navigate(
                R.id.action_resultFragment_to_welcomeFragment
            )
        }
    }
}