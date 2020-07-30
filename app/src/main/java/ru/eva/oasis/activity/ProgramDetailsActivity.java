package ru.eva.oasis.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import ru.eva.oasis.R;
import ru.eva.oasis.model.Program;
import ru.eva.oasis.repository.Storage;

public class ProgramDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_details);

        int id = getIntent().getIntExtra("id", 0);
        Program program = Storage.getInstance().getProgramList().get(id);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(program.getCompany());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppCompatTextView description= findViewById(R.id.description);
        description.setText(program.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}