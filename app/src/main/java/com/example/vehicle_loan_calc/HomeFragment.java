package com.example.vehicle_loan_calc;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    EditText price, down, period, rate;
    TextView loanAmount, totalInterest, totalPayment, monthlyPayment;
    Button calculate, reset;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        price = v.findViewById(R.id.inputPrice);
        down = v.findViewById(R.id.inputDown);
        period = v.findViewById(R.id.inputPeriod);
        rate = v.findViewById(R.id.inputRate);

        loanAmount = v.findViewById(R.id.outputLoanAmount);
        totalInterest = v.findViewById(R.id.outputInterest);
        totalPayment = v.findViewById(R.id.outputTotal);
        monthlyPayment = v.findViewById(R.id.outputMonthly);

        calculate = v.findViewById(R.id.btnCalc);
        reset = v.findViewById(R.id.btnReset);

        // AUTO FORMAT NUMBER
        addNumberFormatter(price);
        addNumberFormatter(down);

        // CALCULATE BUTTON
        calculate.setOnClickListener(view -> {

            double p = Double.parseDouble(price.getText().toString().replace(",", ""));
            double d = Double.parseDouble(down.getText().toString().replace(",", ""));
            int y = Integer.parseInt(period.getText().toString());
            double r = Double.parseDouble(rate.getText().toString());

            double loan = p - d;
            double interest = loan * (r / 100) * y;
            double total = loan + interest;
            double monthly = total / (y * 12);

            loanAmount.setText("Loan Amount: RM " + String.format("%,.2f", loan));
            totalInterest.setText("Interest: RM " + String.format("%,.2f", interest));
            totalPayment.setText("Total Payment: RM " + String.format("%,.2f", total));
            monthlyPayment.setText("Monthly Payment: RM " + String.format("%,.2f", monthly));
        });

        // RESET BUTTON (BETUL)
        reset.setOnClickListener(view -> {
            price.setText("");
            down.setText("");
            period.setText("");
            rate.setText("");

            loanAmount.setText("Loan Amount: RM -");
            totalInterest.setText("Interest: RM -");
            totalPayment.setText("Total Payment: RM -");
            monthlyPayment.setText("Monthly Payment: RM -");
        });

        return v;
    }

    // AUTO FORMAT FUNCTION
    private void addNumberFormatter(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            private String current = "";

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().equals(current)) {
                    editText.removeTextChangedListener(this);

                    String clean = s.toString().replace(",", "");

                    if (!clean.isEmpty()) {
                        double num = Double.parseDouble(clean);
                        String formatted = String.format("%,.0f", num); // BETUL
                        current = formatted;
                        editText.setText(formatted);
                        editText.setSelection(formatted.length());
                    }

                    editText.addTextChangedListener(this);
                }
            }
        });
    }
}
