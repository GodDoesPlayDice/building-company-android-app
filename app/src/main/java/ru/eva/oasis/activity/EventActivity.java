package ru.eva.oasis.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ru.eva.oasis.R;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        TextView textView = findViewById(R.id.textView);
        textView.setText("Событие № " + getIntent().getIntExtra("id", -1));
    }
}