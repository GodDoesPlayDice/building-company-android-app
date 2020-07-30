package ru.eva.oasis.fragment.mortgage;

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

import java.util.List;

import ru.eva.oasis.activity.ProgramActivity;
import ru.eva.oasis.R;
import ru.eva.oasis.activity.ProgramDetailsActivity;
import ru.eva.oasis.adapter.mortgage.MortgageModeAdapter;
import ru.eva.oasis.interfaces.OnBottomSheetItemClick;
import ru.eva.oasis.interfaces.OnItemClickListener;
import ru.eva.oasis.model.Program;
import ru.eva.oasis.repository.Storage;
import ru.eva.oasis.adapter.mortgage.MortgageProgramAdapter;

public class MortgageFragment extends Fragment implements OnBottomSheetItemClick, OnItemClickListener {

    private View root;
    private TextInputEditText mortgageModeText;
    private AppCompatImageView mortgageModeImage;
    private BottomSheetFragment bottomSheetFragment;
    private List<Program> programList;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mortgage, container, false);

        mortgageModeText = root.findViewById(R.id.mortgage_mode_text);
        mortgageModeImage = root.findViewById(R.id.mortgage_mode_image);
        mortgageModeText.setOnClickListener(v-> openBottomSheetFragment());

        mortgageModeImage.setOnClickListener(v-> openBottomSheetFragment());

        AppCompatTextView showAll = root.findViewById(R.id.show_all_text_view);
        showAll.setOnClickListener(v -> startActivity(new Intent(root.getContext(), ProgramActivity.class)));

        AppCompatTextView projectCoastTv = root.findViewById(R.id.project_coast_text_view);
        AppCompatSeekBar projectCoastSb = root.findViewById(R.id.project_coast_seekbar);
        AppCompatTextView initialPaymentTv = root.findViewById(R.id.intial_payment_text_view);
        AppCompatSeekBar initialPaymentSb = root.findViewById(R.id.intial_payment_seekbar);

        projectCoastSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String text = progress*1000+" Р";
                projectCoastTv.setText(text);
                initialPaymentSb.setProgress(calculateInitialPaymentProgress(progress));
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


        programList = Storage.getInstance().getProgramList();
        MortgageProgramAdapter adapter = new MortgageProgramAdapter(programList);
        adapter.setOnItemClickListener(this);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        AppCompatButton submit = root.findViewById(R.id.submit_btn);
        submit.setOnClickListener(v -> { });

        return root;
    }

    private int calculateInitialPaymentProgress(int progress) {
        return progress/10;
    }

    private void openBottomSheetFragment() {
        bottomSheetFragment = BottomSheetFragment.newInstance(mortgageModeText.getText().toString());
        bottomSheetFragment.setOnItemClickListener(this);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void onClick(String name) {
        mortgageModeText.setText(name);
        bottomSheetFragment.dismiss();
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(root.getContext(), ProgramDetailsActivity.class)
                .putExtra("id", programList.get(position).getId()));
    }
}