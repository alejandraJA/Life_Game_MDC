package com.example.lifegame

import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var gameOfLife: GameOfLife
    private lateinit var gridView: GridView
    private lateinit var gridAdapter: GridAdapter
    private lateinit var startStopButton: Button
    private lateinit var randomButton: Button
    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        startStopButton = findViewById(R.id.startStopButton)
        randomButton = findViewById(R.id.randomButton)

        // Inicializar el juego y el adaptador
        gameOfLife = GameOfLife(40) // Tamaño del tablero
//        gameOfLife.initializeRandom() // Inicializar aleatoriamente
        gridAdapter = GridAdapter(this, gameOfLife)
        gridView.adapter = gridAdapter

        // Manejar el clic del botón de inicio/detención
        startStopButton.setOnClickListener {
            if (isRunning) {
                stopGame()
            } else {
                startGame()
            }
        }

        randomButton.setOnClickListener {
            gameOfLife.initializeRandom()
            startGame()
        }
    }

    private fun startGame() {
        isRunning = true
        startStopButton.text = "Stop"
        Thread {
            while (isRunning) {
                Thread.sleep(1000) // Pausa de 1 segundo entre generaciones
                runOnUiThread {
                    gameOfLife.evolve()
                    gridAdapter.notifyDataSetChanged() // Actualizar el GridView
                }
            }
        }.start()
    }

    private fun stopGame() {
        isRunning = false
        startStopButton.text = "Start"
    }
}
