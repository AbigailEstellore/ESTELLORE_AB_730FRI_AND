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
    private boolean isOperatorPressed = false;

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
                if (!display.getText().toString().contains(".")) {
                    display.append(".");
                }
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (firstNumber == null) {
                    firstNumber = Double.parseDouble(display.getText().toString());
                    currentOperator = buttonText;
                    display.setText("");
                    isOperatorPressed = true;
                } else {
                    calculate();
                    currentOperator = buttonText;
                    display.setText(String.valueOf(firstNumber));
                }
                break;
            default:
                if (isOperatorPressed) {
                    display.setText("");
                    isOperatorPressed = false;
                }
                display.append(buttonText);
                break;
        }
    }

    private void calculate() {
        if (firstNumber != null && currentOperator != null) {
            double secondNumber = Double.parseDouble(display.getText().toString());

            switch (currentOperator) {
                case "+":
                    firstNumber += secondNumber;
                    break;
                case "-":
                    firstNumber -= secondNumber;
                    break;
                case "*":
                    firstNumber *= secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        firstNumber /= secondNumber;
                    } else {
                        display.setText("Error");
                        firstNumber = null;
                        currentOperator = null;
                        return;
                    }
                    break;
            }

            display.setText(String.valueOf(firstNumber));
            firstNumber = null;
            currentOperator = null;
        }
    }

    private void clear() {
        display.setText("");
        firstNumber = null;
        currentOperator = null;
        isOperatorPressed = false;
    }
}
