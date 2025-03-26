package com.example.task21p;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Spinner sourceUnitSpinner, destinationUnitSpinner;
    private EditText valueInput;
    private TextView outputText;
    private Button convertButton;

    private Map<String, Double> lengthUnits;
    private Map<String, Double> weightUnits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        sourceUnitSpinner = findViewById(R.id.sourceUnitSprinner);
        destinationUnitSpinner = findViewById(R.id.DestinationUnitSprinner);
        valueInput = findViewById(R.id.valueEnter);
        outputText = findViewById(R.id.outputText);
        convertButton = findViewById(R.id.convertButton);

        // Initialize unit conversion maps
        initializeUnitMaps();

        // Populate the spinners
        String[] unitOptions = {"inch", "foot", "yard", "mile", "cm", "km", "pound", "ounce", "ton", "kg", "g", "Celsius", "Fahrenheit", "Kelvin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, unitOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapter);
        destinationUnitSpinner.setAdapter(adapter);

        // Set click listener for conversion
        convertButton.setOnClickListener(view -> convertValue());
    }

    private void initializeUnitMaps() {
        // Length conversion factors (relative to cm)
        lengthUnits = new HashMap<>();
        lengthUnits.put("inch", 2.54);
        lengthUnits.put("foot", 30.48);
        lengthUnits.put("yard", 91.44);
        lengthUnits.put("mile", 160934.0);
        lengthUnits.put("cm", 1.0);
        lengthUnits.put("km", 100000.0);

        // Weight conversion factors (relative to kg)
        weightUnits = new HashMap<>();
        weightUnits.put("pound", 0.453592);
        weightUnits.put("ounce", 0.0283495);
        weightUnits.put("ton", 907.185);
        weightUnits.put("kg", 1.0);
        weightUnits.put("g", 0.001);
    }

    private void convertValue() {
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();
        String inputValue = valueInput.getText().toString();

        if (inputValue.isEmpty()) {
            outputText.setText("Enter a value!");
            return;
        }

        double value = Double.parseDouble(inputValue);
        double result;

        if (lengthUnits.containsKey(sourceUnit) && lengthUnits.containsKey(destinationUnit)) {
            // Convert length units
            result = value * (lengthUnits.get(sourceUnit) / lengthUnits.get(destinationUnit));
        } else if (weightUnits.containsKey(sourceUnit) && weightUnits.containsKey(destinationUnit)) {
            // Convert weight units
            result = value * (weightUnits.get(sourceUnit) / weightUnits.get(destinationUnit));
        } else if (sourceUnit.equals("Celsius") && destinationUnit.equals("Fahrenheit")) {
            result = (value * 1.8) + 32;
        } else if (sourceUnit.equals("Fahrenheit") && destinationUnit.equals("Celsius")) {
            result = (value - 32) / 1.8;
        } else if (sourceUnit.equals("Celsius") && destinationUnit.equals("Kelvin")) {
            result = value + 273.15;
        } else if (sourceUnit.equals("Kelvin") && destinationUnit.equals("Celsius")) {
            result = value - 273.15;
        } else {
            outputText.setText("Invalid Conversion");
            return;
        }

        outputText.setText(String.format("Output: %.4f %s", result, destinationUnit));
    }
}
