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
            case "C":
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
                if (firstNumber != null) {
                    calculate();
                }
                // Remove any trailing decimal point before adding the operator
                String displayText = display.getText().toString();
                if (displayText.endsWith(".")) {
                    displayText = displayText.substring(0, displayText.length() - 1);
                }
                display.setText(String.format("%s %s ", displayText, buttonText));
                firstNumber = Double.parseDouble(displayText);
                currentOperator = buttonText;
                hasDecimalPoint = false;
                break;
            default:
                if (display.getText().toString().equals("0") || display.getText().toString().equals("Error")) {
                    display.setText(buttonText);
                } else {
                    display.append(buttonText);
                }
                break;
        }
    }


    private void calculate() {
        if (firstNumber != null && currentOperator != null) {
            String text = display.getText().toString();
            // Extract the parts of the expression
            String[] parts = text.split(" ");
            if (parts.length < 3) {
                display.setText("Error");
                return;
            }

            double secondNumber;
            try {
                secondNumber = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                display.setText("Error");
                return;
            }

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
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        display.setText("Error");
                        return;
                    }
                    break;
                default:
                    display.setText("Error");
                    return;
            }

            // Format result based on whether it is a whole number or decimal
            if (result % 1 == 0) {
                display.setText(String.format("%d", (int) result));
            } else {
                display.setText(String.format("%.2f", result));
            }

            firstNumber = result;
            currentOperator = null;
            hasDecimalPoint = result % 1 != 0;
        }
    }




    private void clear() {
        display.setText("0");
        firstNumber = null;
        currentOperator = null;
        hasDecimalPoint = false;
    }
}
