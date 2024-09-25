package com.visenai.mycalcu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private Double firstNumber = null;
    private String currentOperator = null;
    private boolean hasDecimalPoint = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        display = findViewById(R.id.display);

        View.OnClickListener listener = this::onButtonClick;
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnDecimal, R.id.btnEquals, R.id.btnClear
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }
    }

    private void onButtonClick(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        switch (buttonText) {
            case "AC":
                clear();
                break;
            case "=":
                calculate();
                break;
            case ".":
                if (!hasDecimalPoint) {
                    display.append(".");
                    hasDecimalPoint = true;
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                handleOperator(buttonText);
                break;
            default:
                display.append(buttonText);
        }
    }

    private void handleOperator(String operator) {
        String displayText = display.getText().toString();

        if (firstNumber == null) {
            firstNumber = Double.parseDouble(displayText);
            currentOperator = operator;
            display.append(" " + operator + " ");
        } else {
            calculate();
            currentOperator = operator;
            display.append(" " + operator + " ");
        }

        hasDecimalPoint = false;  // Reset decimal point for the next number
    }

    private void calculate() {
        String[] tokens = display.getText().toString().split(" ");
        if (tokens.length < 3) return;

        double secondNumber = Double.parseDouble(tokens[2]);

        double result;
        switch (currentOperator) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "*":
                result = firstNumber * secondNumber;
                break;
            case "/":
                result = firstNumber / secondNumber;
                break;
            default:
                return;
        }

        // Check if either number is a decimal and format accordingly
        String firstNumStr = tokens[0];  // The first number as a string
        boolean isFirstDecimal = firstNumStr.contains(".");
        boolean isSecondDecimal = tokens[2].contains(".");

        // Format result to two decimal places if either number is a decimal
        if (isFirstDecimal || isSecondDecimal) {
            display.setText(String.format("%.2f", result));
        } else {
            // Otherwise, display as an integer
            display.setText(String.format("%.0f", result));
        }

        // Reset for the next calculation
        firstNumber = null;
        currentOperator = null;
        hasDecimalPoint = false;
    }

    private void clear() {
        display.setText("");
        firstNumber = null;
        currentOperator = null;
        hasDecimalPoint = false;
    }
}
