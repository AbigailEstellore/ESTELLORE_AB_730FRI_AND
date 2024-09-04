package com.visenai.mycalcu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply window insets to handle edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize display
        display = findViewById(R.id.display);

        // Set up number and operator listeners
        setNumberListeners();
        setOperatorListeners();
    }

    private void setNumberListeners() {
        int[] numberButtons = {
                R.id.button0, R.id.button1, R.id.button2,
                R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8,
                R.id.button9, R.id.buttonDecimal
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(v -> {
                Button button = (Button) v;
                currentInput += button.getText().toString();
                display.setText(currentInput);
            });
        }
    }

    private void setOperatorListeners() {
        int[] operatorButtons = {
                R.id.buttonAdd, R.id.buttonSubtract,
                R.id.buttonMultiply, R.id.buttonDivide
        };

        for (int id : operatorButtons) {
            findViewById(id).setOnClickListener(v -> {
                if (!currentInput.isEmpty()) {
                    firstNumber = Double.parseDouble(currentInput);
                    currentInput = "";
                    Button button = (Button) v;
                    operator = button.getText().toString();
                }
            });
        }

        findViewById(R.id.buttonEquals).setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                double secondNumber = Double.parseDouble(currentInput);
                double result = performCalculation(firstNumber, secondNumber, operator);
                display.setText(String.valueOf(result));
                currentInput = "";
                operator = "";
            }
        });
    }

    private double performCalculation(double firstNumber, double secondNumber, String operator) {
        switch (operator) {
            case "+":
                return firstNumber + secondNumber;
            case "-":
                return firstNumber - secondNumber;
            case "*":
                return firstNumber * secondNumber;
            case "/":
                if (secondNumber != 0) {
                    return firstNumber / secondNumber;
                } else {
                    display.setText("Error");
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }
}
