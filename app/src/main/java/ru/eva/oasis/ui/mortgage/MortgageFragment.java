package ru.eva.oasis.ui.mortgage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import ru.eva.oasis.ProgramActivity;
import ru.eva.oasis.R;
import ru.eva.oasis.interfaces.OnBottomSheetItemClick;
import ru.eva.oasis.repository.Storage;
import ru.eva.oasis.ui.mortgage.adapter.MortgageProgramAdapter;

public class MortgageFragment extends Fragment implements OnBottomSheetItemClick {

    private View root;
    private TextInputEditText mortgageModeText;
    private AppCompatImageView mortgageModeImage;
    private BottomSheetFragment bottomSheetFragment;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mortgage, container, false);

        mortgageModeText = root.findViewById(R.id.mortgage_mode_text);
        mortgageModeImage = root.findViewById(R.id.mortgage_mode_image);
        mortgageModeText.setOnClickListener(v->{
            bottomSheetFragment = BottomSheetFragment.newInstance(mortgageModeText.getText().toString());
            bottomSheetFragment.setOnItemClickListener(this);
            bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
        });

        mortgageModeImage.setOnClickListener(v->{
            bottomSheetFragment = BottomSheetFragment.newInstance(mortgageModeText.getText().toString());
            bottomSheetFragment.setOnItemClickListener(this);
            bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
        });

        AppCompatTextView showAll = root.findViewById(R.id.show_all_text_view);
        showAll.setOnClickListener(v -> {
            startActivity(new Intent(root.getContext(), ProgramActivity.class));
        });

        AppCompatTextView projectCoastTv = root.findViewById(R.id.project_coast_text_view);
        AppCompatSeekBar projectCoastSb = root.findViewById(R.id.project_coast_seekbar);
        AppCompatTextView initialPaymentTv = root.findViewById(R.id.intial_payment_text_view);
        AppCompatSeekBar initialPaymentSb = root.findViewById(R.id.intial_payment_seekbar);

        projectCoastSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String text = progress*1000+" Р";
                projectCoastTv.setText(text);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        initialPaymentSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String text = progress*1000+" Р";
                initialPaymentTv.setText(text);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
        });


        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(new MortgageProgramAdapter(Storage.getInstance().getProgramList()));

        AppCompatButton submit = root.findViewById(R.id.submit_btn);
        submit.setOnClickListener(v -> { });

        return root;
    }

    @Override
    public void onClick(String name) {
        mortgageModeText.setText(name);
        bottomSheetFragment.dismiss();
    }
}