package ru.eva.oasis.helper;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputEditText;

public class Convert {
    private static volatile Convert instance;

    public static Convert getInstance() {
        if (instance != null)
            return instance;
        return new Convert();
    }

    public double stringToDouble(AppCompatTextView text) {
        return Double.parseDouble(text.getText().toString());
    }

    public int stringToInt(AppCompatTextView text) {
        return Integer.parseInt(text.getText().toString());
    }

    public int stringToInt(TextInputEditText text) {
        return Integer.parseInt(text.getText().toString());
    }
}
