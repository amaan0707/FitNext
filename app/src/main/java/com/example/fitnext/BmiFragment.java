package com.example.fitnext;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;


public class BmiFragment extends Fragment {
    TextInputEditText textInputHeight;
    TextInputEditText textInputWeight;
    TextInputEditText textInputAge;
    Button buttonCalculate;
    TextView textViewResult;
    public BmiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bmi, container, false);

        textInputAge=view.findViewById(R.id.textInputAge);
        textInputWeight=view.findViewById(R.id.textInputWeight);
        textInputHeight=view.findViewById(R.id.textInputHeight);
        textViewResult=view.findViewById(R.id.textViewResult);
        buttonCalculate=view.findViewById(R.id.buttonCalculate);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String weightStr = textInputWeight.getText().toString();
                String heightStr = textInputHeight.getText().toString();
                String ageStr = textInputAge.getText().toString();

                if (!weightStr.isEmpty() && !heightStr.isEmpty() && !ageStr.isEmpty()) {
                    // Convert input to numbers
                    double weight = Double.parseDouble(weightStr);
                    double height = Double.parseDouble(heightStr);
                    int age = Integer.parseInt(ageStr);

                    // Calculate BMI
                    double bmi = weight / (height * height);

                    // Determine health status
                    String healthStatus;
                    if (age < 2) {
                        healthStatus = "BMI categories are not applicable for individuals under 2.";
                    } else if (age < 18) {
                        healthStatus = getChildrenHealthStatus(bmi);
                    } else {
                        healthStatus = getAdultsHealthStatus(bmi);
                    }

                    // Display the result
                    textViewResult.setText("Your BMI is: " + String.format("%.2f", bmi) + "\n" + healthStatus);
                } else {
                    textViewResult.setText("Please enter weight, height, and age.");
                }
            }
        });
        return view;
    }
    private String getChildrenHealthStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight for age";
        } else if (bmi < 24.9) {
            return "Normal weight for age";
        } else if (bmi < 29.9) {
            return "Overweight for age";
        } else {
            return "Obese for age";
        }
    }

    private String getAdultsHealthStatus(double bmi) {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 24.9) {
            return "Healthy weight";
        } else if (bmi < 29.9) {
            return "Overweight";
        } else if (bmi < 34.9) {
            return "Obese (Class 1)";
        } else if (bmi < 39.9) {
            return "Obese (Class 2)";
        } else {
            return "Extreme Obese (Class 3)";
        }
    }
}