package com.nicolle.juegocolores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter para el RecyclerView que muestra el historial de puntajes
 * Gestiona la creación y vinculación de vistas para cada item del historial
 */
class HistorialAdapter(
    private val listaPuntajes: List<Int>
) : RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    /**
     * ViewHolder que contiene las referencias a las vistas de cada item
     */
    class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumeroPartida: TextView = itemView.findViewById(R.id.tvNumeroPartida)
        val tvPuntajePartida: TextView = itemView.findViewById(R.id.tvPuntajePartida)
        val tvIconoMedalla: TextView = itemView.findViewById(R.id.tvIconoMedalla)
    }

    /**
     * Crea un nuevo ViewHolder cuando es necesario
     * Se llama automáticamente por el RecyclerView
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        // Infla el layout del item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    /**
     * Vincula los datos con las vistas del ViewHolder
     * @param holder El ViewHolder a vincular
     * @param position La posición del item en la lista
     */
    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        // Obtiene el puntaje en la posición actual
        val puntaje = listaPuntajes[position]

        // Configura el número de partida (posición + 1 porque empieza en 0)
        holder.tvNumeroPartida.text = "Partida #${position + 1}"

        // Configura el puntaje
        holder.tvPuntajePartida.text = "$puntaje puntos"

        // Determina si este puntaje es el máximo de la sesión
        val puntajeMaximo = listaPuntajes.maxOrNull() ?: 0

        // Muestra la medalla solo para el puntaje más alto
        if (puntaje == puntajeMaximo && puntaje > 0) {
            holder.tvIconoMedalla.visibility = View.VISIBLE
        } else {
            holder.tvIconoMedalla.visibility = View.GONE
        }
    }

    /**
     * Retorna el número total de items en el historial
     */
    override fun getItemCount(): Int {
        return listaPuntajes.size
    }
}