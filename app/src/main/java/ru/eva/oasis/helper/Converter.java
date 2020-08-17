package ru.eva.oasis.helper;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.textfield.TextInputEditText;

public class Converter {
    private static volatile Converter instance;

    public static Converter getInstance() {
        if (instance != null)
            return instance;
        return new Converter();
    }
}
