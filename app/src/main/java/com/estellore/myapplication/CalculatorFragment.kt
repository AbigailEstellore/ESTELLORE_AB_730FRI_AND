package com.estellore.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class CalculatorFragment : Fragment() {

    private lateinit var display: TextView
    private var firstNumber: Double? = null
    private var currentOperator: String? = null
    private var hasDecimalPoint: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main)) { v, insets ->
            val systemBars: Insets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        display = view.findViewById(R.id.display)

        val listener = View.OnClickListener { v -> onButtonClick(v) }
        val buttonIds = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
            R.id.btnDecimal, R.id.btnEquals, R.id.btnClear
        )

        for (id in buttonIds) {
            view.findViewById<View>(id).setOnClickListener(listener)
        }
    }

    private fun onButtonClick(v: View) {
        val button = v as Button
        val buttonText = button.text.toString()

        when (buttonText) {
            "AC" -> clear()
            "=" -> calculate()
            "." -> {
                if (!hasDecimalPoint) {
                    display.append(".")
                    hasDecimalPoint = true
                }
            }
            "+", "-", "*", "/" -> handleOperator(buttonText)
            else -> display.append(buttonText)
        }
    }

    private fun handleOperator(operator: String) {
        val displayText = display.text.toString()

        if (firstNumber == null) {
            firstNumber = displayText.toDouble()
            currentOperator = operator
            display.append(" $operator ")
        } else {
            calculate()
            currentOperator = operator
            display.append(" $operator ")
        }

        hasDecimalPoint = false  // Reset decimal point for the next number
    }

    private fun calculate() {
        val tokens = display.text.toString().split(" ")
        if (tokens.size < 3) return

        val secondNumber = tokens[2].toDouble()

        val result = when (currentOperator) {
            "+" -> firstNumber!! + secondNumber
            "-" -> firstNumber!! - secondNumber
            "*" -> firstNumber!! * secondNumber
            "/" -> firstNumber!! / secondNumber
            else -> return
        }

        // Check if either number is a decimal and format accordingly
        val firstNumStr = tokens[0]
        val isFirstDecimal = firstNumStr.contains(".")
        val isSecondDecimal = tokens[2].contains(".")

        // Format result to two decimal places if either number is a decimal
        if (isFirstDecimal || isSecondDecimal) {
            display.text = String.format("%.2f", result)
        } else {
            // Otherwise, display as an integer
            display.text = String.format("%.0f", result)
        }

        // Reset for the next calculation
        firstNumber = null
        currentOperator = null
        hasDecimalPoint = false
    }

    private fun clear() {
        display.text = ""
        firstNumber = null
        currentOperator = null
        hasDecimalPoint = false
    }
}
