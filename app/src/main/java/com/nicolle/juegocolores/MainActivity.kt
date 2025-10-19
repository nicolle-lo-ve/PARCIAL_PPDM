package com.nicolle.juegocolores

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

/**
 * Actividad principal que contiene el NavHostFragment
 * Gestiona la navegación entre los diferentes fragments del juego
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar la Navigation Component
        // Obtiene el NavHostFragment que contiene el grafo de navegación
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configura la ActionBar para trabajar con Navigation Component
        // Esto permite mostrar el botón de "atrás" automáticamente
        setupActionBarWithNavController(navController)
    }

    /**
     * Maneja el evento de presionar el botón "atrás"
     * Delega la navegación al NavController
     */
    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Si no puede navegar hacia atrás, termina la actividad
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}