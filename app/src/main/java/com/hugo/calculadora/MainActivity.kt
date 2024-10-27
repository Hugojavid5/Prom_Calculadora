package com.hugo.calculadora

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hugo.calculadora.databinding.ActivityMainBinding

/**
 * La actividad principal para la calculadora.
 * Implementa View.OnClickListener para gestionar eventos de los botones.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {

    // View binding para acceder a los elementos de la UI de manera eficiente.
    private lateinit var binding: ActivityMainBinding

    // Primer número en la operación.
    private var firstNumber = 0.0

    // Segundo número en la operación.
    private var secondNumber = 0.0

    // Operación matemática seleccionada.
    private var operation: String? = null

    /**
     * Método llamado al crear la actividad.
     * Inicializa el binding y configura los listeners para los botones de la calculadora.
     *
     * @param savedInstanceState Estado de la instancia de la actividad.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa `binding` utilizando el método `inflate`
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Usa `binding.root` en lugar de `R.layout.activity_main`

        // Inicialización de botones
        operation = null
        binding.btn0.setOnClickListener(this)
        binding.btn1.setOnClickListener(this)
        binding.btn2.setOnClickListener(this)
        binding.btn3.setOnClickListener(this)
        binding.btn4.setOnClickListener(this)
        binding.btn5.setOnClickListener(this)
        binding.btn6.setOnClickListener(this)
        binding.btn7.setOnClickListener(this)
        binding.btn8.setOnClickListener(this)
        binding.btn9.setOnClickListener(this)
        binding.btnBorrar.setOnClickListener(this)
        binding.btnSumar.setOnClickListener(this)
        binding.btnMenos.setOnClickListener(this)
        binding.btnMul.setOnClickListener(this)
        binding.btnDiv.setOnClickListener(this)
        binding.btnIgual.setOnClickListener(this)
        binding.btnComa.setOnClickListener(this)
    }

    /**
     * Método llamado al hacer clic en cualquiera de los botones de la calculadora.
     *
     * @param view La vista del botón que se hizo clic.
     */
    override fun onClick(view: View?) {
        when (view) {
            binding.btn0 -> onNumberPresses("0")
            binding.btn1 -> onNumberPresses("1")
            binding.btn2 -> onNumberPresses("2")
            binding.btn3 -> onNumberPresses("3")
            binding.btn4 -> onNumberPresses("4")
            binding.btn5 -> onNumberPresses("5")
            binding.btn6 -> onNumberPresses("6")
            binding.btn7 -> onNumberPresses("7")
            binding.btn8 -> onNumberPresses("8")
            binding.btn9 -> onNumberPresses("9")
            binding.btnComa -> onNumberPresses(",")
            binding.btnSumar -> onOperationPresed("+")
            binding.btnMenos -> onOperationPresed("-")
            binding.btnMul -> onOperationPresed("x")
            binding.btnDiv -> onOperationPresed("/")
            binding.btnIgual -> onEqualPressed()
            binding.btnBorrar -> onClearPressed()
        }
    }

    /**
     * Llamado al presionar un botón numérico.
     *
     * @param number El número presionado como una cadena.
     */
    private fun onNumberPresses(number: String) {
        renderScreen(number)
        checkOperation()
    }

    /**
     * Renderiza el número en la pantalla de la calculadora.
     *
     * @param number El número a mostrar.
     */
    private fun renderScreen(number: String) {
        val result: String = if (binding.screen.text == "0" && number != ",") number else "${binding.screen.text}$number"
        binding.screen.text = result
    }

    /**
     * Verifica si una operación está seleccionada y asigna valores a `firstNumber` o `secondNumber`.
     */
    private fun checkOperation() {
        if (operation == null)
            firstNumber = binding.screen.text.toString().toDouble()
        else
            secondNumber = binding.screen.text.toString().toDouble()
    }

    /**
     * Llamado al presionar un botón de operación (+, -, *, /).
     * Establece la operación seleccionada y reinicia la pantalla.
     *
     * @param operation La operación seleccionada como una cadena.
     */
    private fun onOperationPresed(operation: String) {
        this.operation = operation
        firstNumber = binding.screen.text.toString().toDouble()
        binding.screen.text = "0"
    }

    /**
     * Calcula el resultado basado en los números ingresados y la operación seleccionada.
     * Muestra el resultado en la pantalla.
     */
    private fun onEqualPressed() {
        // Evita dividir por cero
        if (operation == "/" && secondNumber == 0.0) {
            binding.screen.text = "Error" // Muestra error en la pantalla
            return
        }
        val result = when (operation) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "x" -> firstNumber * secondNumber
            "/" -> firstNumber / secondNumber
            else -> 0.0
        }

        // Reset de operación y asignación de resultado
        operation = null
        firstNumber = result
        secondNumber = 0.0

        // Mostrar resultado en pantalla
        binding.screen.text = if (result.toString().endsWith(".0")) {
            result.toInt().toString()
        } else {
            "%.2f".format(result)
        }
    }

    /**
     * Reinicia los valores de la calculadora y borra la pantalla.
     */
    private fun onClearPressed() {
        binding.screen.text = "0"
        firstNumber = 0.0
        secondNumber = 0.0
    }
}
