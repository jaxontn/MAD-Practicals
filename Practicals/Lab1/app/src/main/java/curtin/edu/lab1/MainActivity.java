package curtin.edu.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2;
    Button add, minus, divide, multiply;
    TextView ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //involving some important setting up
        setContentView(R.layout.activity_main); //the UI screen activity_main.xml

        //all code should be under setContentView(R.layout.activity_main);

        //linking global variables with the gui elements
        num1 = findViewById(R.id.number1);
        num2 = findViewById(R.id.number2);
        add = findViewById(R.id.addition);
        minus = findViewById(R.id.subtract);
        divide = findViewById(R.id.divide);
        multiply = findViewById(R.id.multiply);
        ans = findViewById(R.id.result);


        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                try {
                    double i = Double.parseDouble(num1.getText().toString());
                    double j = Double.parseDouble(num2.getText().toString());
                    double result = i + j;
                    ans.setText( i + " + " + j + " = " + String.valueOf(result));
                }
                catch(Exception e)
                {
                    ans.setText("Error: please ensure enter only Integer of Double");
                }
            }
        });


        minus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                try {
                    double i = Double.parseDouble(num1.getText().toString());
                    double j = Double.parseDouble(num2.getText().toString());
                    double result = i - j;
                    ans.setText(i + " - " + j + " = " + String.valueOf(result));
                }
                catch(Exception e)
                {
                    ans.setText("Error: please ensure enter only Integer of Double");
                }
            }
        });



        divide.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                try {

                    double i = Double.parseDouble(num1.getText().toString());
                    double j = Double.parseDouble(num2.getText().toString());
                    double result = i / j;
                    ans.setText( i + " / " + j + " = " + String.valueOf(result));
                }
                catch(Exception e)
                {
                    ans.setText("Error: please ensure enter only Integer of Double");
                }
            }
        });


        multiply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) //do something when the button is pressed
            {
                try{
                    double i = Double.parseDouble(num1.getText().toString());
                    double j = Double.parseDouble(num2.getText().toString());
                    double result = i * j;
                    ans.setText( i + " * " + j + " = " + String.valueOf(result));
                }
                catch(Exception e)
                {
                    ans.setText("Error: please ensure enter only Integer of Double");
                }
            }
        });
    }
}