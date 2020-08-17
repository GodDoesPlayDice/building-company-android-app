package ru.eva.oasis.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import ru.eva.oasis.R;
import ru.eva.oasis.interfaces.OnMortgageProgramReceived;
import ru.eva.oasis.model.MortgageProgram;
import ru.eva.oasis.model.Program;
import ru.eva.oasis.repository.Firebase;
import ru.eva.oasis.repository.Storage;

public class MortgageProgramDetailsActivity extends AppCompatActivity implements OnMortgageProgramReceived {

    private Toolbar toolbar;
    private AppCompatTextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_details);

        toolbar = findViewById(R.id.toolbar);
        description = findViewById(R.id.description);

        String id = getIntent().getStringExtra("id");
        Firebase.getInstance().getMortgageProgramById(id, this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponse(MortgageProgram program) {
        toolbar.setTitle(program.getProgramName());
        toolbar.setSubtitle(program.getBankName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        description.setText(program.getTextDescription());
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}