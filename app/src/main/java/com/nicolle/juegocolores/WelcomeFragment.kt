package com.nicolle.juegocolores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Fragment de bienvenida que muestra el título del juego y las opciones para iniciar
 * Incluye un AlertDialog con las reglas del juego
 */
class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout del fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Referencias a los botones del layout
        val btnIniciarJuego = view.findViewById<View>(R.id.btnIniciarJuego)
        val btnVerReglas = view.findViewById<View>(R.id.btnVerReglas)

        // Configura el click del botón "Iniciar Juego"
        // Navega directamente al GameFragment
        btnIniciarJuego.setOnClickListener {
            navegarAlJuego()
        }

        // Configura el click del botón "Ver Reglas"
        // Muestra un AlertDialog con las instrucciones del juego
        btnVerReglas.setOnClickListener {
            mostrarDialogoReglas()
        }
    }

    /**
     * Navega al fragment del juego utilizando Navigation Component
     */
    private fun navegarAlJuego() {
        findNavController().navigate(
            R.id.action_welcomeFragment_to_gameFragment
        )
    }

    /**
     * Muestra un AlertDialog con las reglas del juego
     * Utiliza MaterialAlertDialogBuilder para un diseño consistente
     */
    private fun mostrarDialogoReglas() {
        // Crea el dialog usando MaterialAlertDialogBuilder
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.titulo_reglas))
            .setMessage(getString(R.string.reglas_contenido))
            .setPositiveButton(getString(R.string.boton_entendido)) { dialog, _ ->
                // Cierra el dialog cuando presiona "Entendido"
                dialog.dismiss()
            }
            .setNeutralButton(getString(R.string.boton_iniciar)) { dialog, _ ->
                // Opción adicional: iniciar juego desde el dialog
                dialog.dismiss()
                navegarAlJuego()
            }
            .setCancelable(true) // Permite cerrar presionando fuera del dialog
            .show()
    }

    /**
     * Se llama cuando el fragment vuelve a estar visible
     * Útil para reiniciar animaciones o estados si fuera necesario
     */
    override fun onResume() {
        super.onResume()
        // Aquí podrías agregar animaciones de entrada si lo deseas
    }
}