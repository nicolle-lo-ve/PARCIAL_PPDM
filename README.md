# JUEGO DE COLORES - EXAMEN PARCIAL

## Información del Proyecto
**Autor**: Nicolle Andrea Lozano Vega  
**Fecha**: 19/10/2025  
**Lenguaje**: Kotlin  
**Plataforma**: Android Studio  
**Versión**: 1.0  

## Descripción
Aplicación de juego interactivo donde el usuario debe presionar el botón que coincide con el color mostrado en pantalla. El objetivo es obtener la mayor cantidad de aciertos posibles en 30 segundos.

El juego implementa navegación entre múltiples fragments, gestión de estados del ciclo de vida, temporizador de cuenta regresiva, sistema de puntuación con historial y persistencia de datos.

## Funcionalidades Implementadas

### Funcionalidades Principales
- **Navegación entre 3 Fragments**: WelcomeFragment, GameFragment, ResultFragment
- **Temporizador de cuenta regresiva**: 30 segundos de juego
- **Sistema de puntuación**: Cada acierto suma 1 punto
- **Generación de colores aleatorios**: 4 colores disponibles (Rojo, Verde, Azul, Amarillo)
- **Feedback visual inmediato**: Mensajes de "Correcto" e "Incorrecto"
- **Persistencia de puntaje máximo**: Usando SharedPreferences
- **Historial de la sesión**: RecyclerView con todas las partidas jugadas
- **AlertDialog con reglas del juego**: Explicación interactiva

### Funcionalidad Adicional Implementada
- **Animaciones en botones y colores**: Efectos visuales con ObjectAnimator en GameFragment

## Estructura del Proyecto

```
app/
├── src/main/
│   ├── java/com/example/juegocolores/
│   │   ├── MainActivity.kt                # Actividad principal con NavHost
│   │   ├── WelcomeFragment.kt             # Pantalla de bienvenida
│   │   ├── GameFragment.kt                # Lógica principal del juego
│   │   ├── ResultFragment.kt              # Pantalla de resultados
│   │   ├── HistorialAdapter.kt            # Adapter del RecyclerView
│   │   └── HistorialManager.kt            # Gestión del historial en memoria
│   │
│   ├── res/
│   │   ├── layout/
│   │   │   ├── activity_main.xml          # Layout de MainActivity
│   │   │   ├── fragment_welcome.xml       # UI de bienvenida
│   │   │   ├── fragment_game.xml          # UI del juego
│   │   │   ├── fragment_result.xml        # UI de resultados
│   │   │   └── item_historial.xml         # Item del RecyclerView
│   │   │
│   │   ├── navigation/
│   │   │   └── nav_graph.xml              # Grafo de navegación
│   │   │
│   │   ├── drawable/
│   │   │   ├── circulo_color.xml          # Shape circular
│   │   │   ├── cuadrado_color.xml         # Shape cuadrado redondeado
│   │   │   └── fondo_panel.xml            # Shape de paneles
│   │   │
│   │   └── values/
│   │       ├── strings.xml                # Textos de la app
│   │       └── colors.xml                 # Paleta de colores
│   │
│   └── AndroidManifest.xml                # Configuración de la app
│
└── build.gradle.kts                       # Dependencias del proyecto
```

## Tecnologías y Conceptos Utilizados

### Componentes de Android
- **Navigation Component**: Navegación tipo-segura entre fragments
- **Fragments**: Arquitectura modular con ciclo de vida
- **RecyclerView**: Lista eficiente de historial
- **SharedPreferences**: Persistencia de datos ligera
- **CountDownTimer**: Temporizador de cuenta regresiva
- **AlertDialog**: Diálogos informativos
- **ConstraintLayout**: Layouts responsivos

### Conceptos de Kotlin
- **Object Singleton**: HistorialManager
- **Data Classes**: Modelos de datos
- **Extension Functions**: Funciones de extensión
- **Safe Args**: Paso seguro de argumentos
- **ViewBinding**: Vinculación de vistas

### Buenas Prácticas
- Código comentado y documentado
- Nombres descriptivos en español
- Separación de responsabilidades
- Manejo del ciclo de vida
- Gestión de memoria (cancelación de timers)
- Uso de recursos XML (strings, colors)


## Cómo Jugar

1. **Pantalla de Bienvenida**
   - Lee las reglas presionando "Ver Reglas"
   - Presiona "Iniciar Juego" para comenzar
     
   - *Pantalla de Bienvenida*
     
     <img width="135" height="292" alt="image" src="https://github.com/user-attachments/assets/292dd94d-6607-480b-9187-4fff47675a7e" />
   
   -  *Botón Ver Reglas*
     
      <img width="135" height="292" alt="image" src="https://github.com/user-attachments/assets/f594c739-c6ea-4dc8-aae8-902495da50c9" />

2. **Durante el Juego**
   - Observa el color que aparece en el cuadrado central
   - Presiona el botón que coincida con ese color
   - Tienes 30 segundos para obtener el mayor puntaje
   - Cada acierto suma 1 punto
  
   - *Pantalla durante el Juego*

      <img width="135" height="292" alt="image" src="https://github.com/user-attachments/assets/cf153510-99e3-4348-90f9-0fcd970948de" />

   - *Presionar botón Correcto*

      <img width="135" height="292" alt="image" src="https://github.com/user-attachments/assets/ba9e96f5-d5e4-482f-bf70-8b9040999ba6" />

   - *Presionar botón Incorrecto*
  
      <img width="135" height="292" alt="image" src="https://github.com/user-attachments/assets/b9b0ebfd-ba1f-447f-aa08-b7d1a2f77fe0" />


3. **Pantalla de Resultados**
   - Ve tu puntaje final
   - Compara con tu récord histórico
   - Revisa el historial de la sesión
   - Juega nuevamente o vuelve al menú
  
   - *Pantalla de Resultados*
  
     <img width="135" height="292" alt="image" src="https://github.com/user-attachments/assets/754cba02-de7c-4e13-a495-cffddd5e5e1e" />


## Decisiones de Diseño

### Colores Elegidos
- **Fondo Principal**: Tono oscuro (#1A1A2E) para reducir fatiga visual
- **Colores del Juego**: Tonos vibrantes y claramente diferenciables
- **Feedback**: Verde para éxito, rojo para error

### Experiencia de Usuario
- **Animaciones sutiles**: Mejoran feedback sin distraer
- **Botones grandes**: Facilitan interacción rápida
- **Contraste alto**: Colores claramente distinguibles
- **Retroalimentación inmediata**: El usuario siempre sabe si acertó

## Manejo de Errores

- **Rotación de pantalla**: Configuración en manifest previene pérdida de estado
- **Memory leaks**: Cancelación apropiada de timers en onDestroyView
- **Null safety**: Uso de operadores seguros de Kotlin
- **Valores por defecto**: En SharedPreferences y funciones

## Compatibilidad

- **API Mínima**: 21 (Android 5.0 Lollipop)
- **API Target**: 34 (Android 14)
- **Orientación**: Portrait (bloqueada para mejor experiencia)

## Flujo de Navegación

```
WelcomeFragment
    ↓
    [Iniciar Juego]
    ↓
GameFragment (30 segundos)
    ↓
    [Tiempo agotado]
    ↓
ResultFragment
    ↓
    [Jugar Otra Vez] → GameFragment
    [Menú Principal] → WelcomeFragment
```

## Posibles Mejoras Futuras

- Integración con Room para persistencia completa
- Sistema de niveles de dificultad (Efecto Stroop)
- Efectos de sonido y música de fondo
- Logros y trofeos
- Más variedad de colores

