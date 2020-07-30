package ru.eva.oasis.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ru.eva.oasis.R;

public class BuildingObjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_object);
        TextView textView = findViewById(R.id.textView);
        textView.setText("Строительный объект № " + getIntent().getIntExtra("id", -1));
    }
}