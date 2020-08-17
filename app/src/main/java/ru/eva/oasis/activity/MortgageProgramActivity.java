package ru.eva.oasis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.eva.oasis.R;
import ru.eva.oasis.adapter.program.ProgramAdapter;
import ru.eva.oasis.interfaces.OnItemClickListener;
import ru.eva.oasis.interfaces.OnMortgageProgramsReceived;
import ru.eva.oasis.model.MortgageProgram;
import ru.eva.oasis.model.Program;
import ru.eva.oasis.repository.Firebase;
import ru.eva.oasis.repository.Storage;

public class MortgageProgramActivity extends AppCompatActivity implements OnItemClickListener, OnMortgageProgramsReceived {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ипотека");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String name = getIntent().getStringExtra("name");
        int projectCoast = getIntent().getIntExtra("projectCoast", 0);
        int initialPayment = getIntent().getIntExtra("initialPayment", 0);
        int age = getIntent().getIntExtra("age", 0);
        int term = getIntent().getIntExtra("term", 0);

        Firebase.getInstance().getMortgageProgram(name, projectCoast, initialPayment, age, term, this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(this, MortgageProgramDetailsActivity.class)
                .putExtra("id", position + ""));
    }

    @Override
    public void onResponse(List<MortgageProgram> programList) {
        ProgramAdapter adapter = new ProgramAdapter(programList);
        adapter.setOnItemClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}