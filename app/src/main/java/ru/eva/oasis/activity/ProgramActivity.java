package ru.eva.oasis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.eva.oasis.R;
import ru.eva.oasis.adapter.program.ProgramAdapter;
import ru.eva.oasis.interfaces.OnItemClickListener;
import ru.eva.oasis.model.Program;
import ru.eva.oasis.repository.Storage;

public class ProgramActivity extends AppCompatActivity implements OnItemClickListener {

    private List<Program> programList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ипотека");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        programList = Storage.getInstance().getProgramList();
        ProgramAdapter adapter = new ProgramAdapter(programList);
        adapter.setOnItemClickListener(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(this, ProgramDetailsActivity.class)
                .putExtra("id", programList.get(position).getId()));
    }
}