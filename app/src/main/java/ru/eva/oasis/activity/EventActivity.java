package ru.eva.oasis.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import ru.eva.oasis.R;
import ru.eva.oasis.interfaces.OnBannerReceived;
import ru.eva.oasis.model.Banner;
import ru.eva.oasis.repository.Firebase;

public class EventActivity extends AppCompatActivity implements OnBannerReceived {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("События");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Firebase.getInstance().getBannerById(getIntent().getStringExtra("id"), this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onResponse(Banner banner) {
        AppCompatTextView textView = findViewById(R.id.title);
        textView.setText(banner.getTitle());
        AppCompatImageView imageView = findViewById(R.id.image);
        Picasso.get().load(banner.getImageUrl()).into(imageView);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}