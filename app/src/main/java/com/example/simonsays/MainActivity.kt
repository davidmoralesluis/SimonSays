package com.example.simonsays

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.simonsays.R.*

class MainActivity : AppCompatActivity() {

    private lateinit var texto: TextView
    private lateinit var startButton: Button
    private lateinit var redButton: Button
    private lateinit var greenButton: Button
    private lateinit var blueButton: Button
    private lateinit var yellowButton: Button

    private val sequenceList = mutableListOf<Button>()
    private val handler = Handler()

    private var playerTurn = false
    private var sequenceIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        texto = findViewById(id.sequenceText)
        startButton = findViewById(id.startButton)
        redButton = findViewById(id.redButton)
        greenButton = findViewById(id.greenButton)
        blueButton = findViewById(id.blueButton)
        yellowButton = findViewById(id.yellowButton)

        startButton.setOnClickListener {
            startGame()
        }

        redButton.setOnClickListener {
            if (playerTurn) {
                checkButtonSequence(redButton)
            }
        }

        greenButton.setOnClickListener {
            if (playerTurn) {
                checkButtonSequence(greenButton)
            }
        }

        blueButton.setOnClickListener {
            if (playerTurn) {
                checkButtonSequence(blueButton)
            }
        }

        yellowButton.setOnClickListener {
            if (playerTurn) {
                checkButtonSequence(yellowButton)
            }
        }
    }

    private fun startGame() {
        sequenceList.clear()
        sequenceIndex = 0
        playerTurn = false
        texto.text = "Simón dice"

        enableButtons(false)
        startButton.isEnabled = false

        handler.postDelayed({
            addRandomButtonToSequence()
            playSequence()
        }, 1000)
    }

    private fun addRandomButtonToSequence() {
        val buttons = listOf(redButton, greenButton, blueButton, yellowButton)
        val randomButton = buttons.random()
        sequenceList.add(randomButton)
    }

    private fun playSequence() {
        sequenceIndex = 0
        playerTurn = false

        handler.postDelayed({
            showNextButtonInSequence()
        }, 1000)
    }


    /*private fun showNextButtonInSequence() {
        if (sequenceIndex < sequenceList.size) {
            val button = sequenceList[sequenceIndex]
            button.setBackgroundResource(getButtonColorResource(button.id))
            handler.postDelayed({
                button.setBackgroundResource(R.drawable.gris)
                sequenceIndex++
                showNextButtonInSequence()
            }, 500)
        } else {
            playerTurn = true
            texto.text = "Tu turno"
            enableButtons(true)
        }
    }*/
    private fun showNextButtonInSequence() {
        if (sequenceIndex < sequenceList.size) {
            val button = sequenceList[sequenceIndex]
            val curtainDrawable = ContextCompat.getDrawable(this, drawable.button_curtain)
            button.background = curtainDrawable
            handler.postDelayed({
                button.setBackgroundResource(getButtonBackground(button.id))
                sequenceIndex++
                showNextButtonInSequence()
            }, 500)
        } else {
            playerTurn = true
            texto.text = "Tu turno"
            enableButtons(true)
        }
    }


    private fun checkButtonSequence(button: Button) {
        if (sequenceIndex < sequenceList.size && sequenceList[sequenceIndex] == button) {
            sequenceIndex++
            if (sequenceIndex == sequenceList.size) {
                playerTurn = false
                texto.text = "Correcto!"
                enableButtons(false)
                handler.postDelayed({
                    startGame()
                }, 1000)
            }
        } else {
            texto.text = "Incorrecto! Fin del juego"
            enableButtons(false)
            startButton.isEnabled = true
        }
    }

    private fun getButtonBackground(buttonId: Int): Int {
        return when (buttonId) {
            id.redButton -> drawable.rojo
            id.greenButton -> drawable.verde
            id.blueButton -> drawable.azul
            id.yellowButton -> drawable.amarillo
            else -> drawable.gris
        }
    }


    private fun enableButtons(enabled: Boolean) {
        redButton.isEnabled = enabled
        greenButton.isEnabled = enabled
        blueButton.isEnabled = enabled
        yellowButton.isEnabled = enabled
    }
}
