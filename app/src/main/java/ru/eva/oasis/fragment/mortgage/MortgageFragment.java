package ru.eva.oasis.fragment.mortgage;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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

import ru.eva.oasis.R;
import ru.eva.oasis.activity.MortgageProgramActivity;
import ru.eva.oasis.activity.MortgageProgramDetailsActivity;
import ru.eva.oasis.adapter.mortgage.MortgageProgramAdapter;
import ru.eva.oasis.interfaces.OnBottomSheetItemClick;
import ru.eva.oasis.interfaces.OnItemClickListener;
import ru.eva.oasis.model.MortgageProgram;

public class MortgageFragment extends Fragment implements ContractMortgage.View,
        OnBottomSheetItemClick,
        OnItemClickListener,
        TextView.OnEditorActionListener {

    private View root;
    private TextInputEditText mortgageModeText;
    private BottomSheetFragment bottomSheetFragment;

    private AppCompatTextView projectCoastTv;
    private AppCompatTextView initialPaymentTv;
    private AppCompatSeekBar initialPaymentSb;
    private TextInputEditText ageEt;
    private TextInputEditText termEt;
    private ContractMortgage.Presenter mPresenter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mortgage, container, false);

        mPresenter = new PresenterMortgage(this);

        mortgageModeText = root.findViewById(R.id.mortgage_mode_text);
        AppCompatImageView mortgageModeImage = root.findViewById(R.id.mortgage_mode_image);
        mortgageModeText.setOnClickListener(v -> openBottomSheetFragment());

        mortgageModeImage.setOnClickListener(v -> openBottomSheetFragment());

        AppCompatTextView showAll = root.findViewById(R.id.show_all_text_view);
        showAll.setOnClickListener(v ->
                mPresenter.startMortgageActivity(mortgageModeText.getText().toString(),
                        projectCoastTv.getText().toString(),
                        initialPaymentTv.getText().toString(),
                        ageEt.getText().toString(),
                        termEt.getText().toString()));

        projectCoastTv = root.findViewById(R.id.project_coast_text_view);
        AppCompatSeekBar projectCostSb = root.findViewById(R.id.project_coast_seekbar);
        initialPaymentTv = root.findViewById(R.id.intial_payment_text_view);
        initialPaymentSb = root.findViewById(R.id.initial_payment_seekbar);

        ageEt = root.findViewById(R.id.age_text_view);
        termEt = root.findViewById(R.id.term_text_view);

        ageEt.setOnEditorActionListener(this);
        termEt.setOnEditorActionListener(this);

        projectCostSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPresenter.onCostProgressChanged(progress, initialPaymentTv.getText().toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPresenter.getMortgageProgram(mortgageModeText.getText().toString(),
                        projectCoastTv.getText().toString(),
                        initialPaymentTv.getText().toString(),
                        ageEt.getText().toString(),
                        termEt.getText().toString());
            }
        });

        initialPaymentSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mPresenter.onPaymentProgressChanged(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mPresenter.getMortgageProgram(mortgageModeText.getText().toString(),
                        projectCoastTv.getText().toString(),
                        initialPaymentTv.getText().toString(),
                        ageEt.getText().toString(),
                        termEt.getText().toString());
            }
        });
        AppCompatButton submit = root.findViewById(R.id.submit_btn);
        submit.setOnClickListener(v -> {

        });

        mPresenter.getMortgageProgram(mortgageModeText.getText().toString(),
                projectCoastTv.getText().toString(),
                initialPaymentTv.getText().toString(),
                ageEt.getText().toString(),
                termEt.getText().toString());

        return root;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            mPresenter.getMortgageProgram(mortgageModeText.getText().toString(),
                    projectCoastTv.getText().toString(),
                    initialPaymentTv.getText().toString(),
                    ageEt.getText().toString(),
                    termEt.getText().toString());
            return event == null || !event.isShiftPressed();
        }
        return false;
    }

    @Override
    public void onClick(String name) {
        mortgageModeText.setText(name);
        bottomSheetFragment.dismiss();
        mPresenter.getMortgageProgram(mortgageModeText.getText().toString(),
                projectCoastTv.getText().toString(),
                initialPaymentTv.getText().toString(),
                ageEt.getText().toString(),
                termEt.getText().toString());
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(root.getContext(), MortgageProgramDetailsActivity.class)
                .putExtra("id", position + ""));
    }

    private void openBottomSheetFragment() {
        bottomSheetFragment = BottomSheetFragment.newInstance(mortgageModeText.getText().toString());
        bottomSheetFragment.setOnItemClickListener(this);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void setAdapter(List<MortgageProgram> programList) {
        MortgageProgramAdapter adapter = new MortgageProgramAdapter(programList);
        adapter.setOnItemClickListener(this);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void setInitialPaymentText(String text) {
        initialPaymentTv.setText(text);
    }

    @Override
    public void setInitialPaymentProgress(int progress) {
        initialPaymentSb.setProgress(progress);
    }

    @Override
    public void setProjectCoastText(String projectCostText) {
        projectCoastTv.setText(projectCostText);
    }

    @Override
    public void setAgeEditTextError(String error) {
        ageEt.setError(error);
    }

    @Override
    public void setTermEditTextError(String error) {
        termEt.setError(error);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMortgageActivity(String mode, int projectCost, int initialPayment, int age, int term) {
        startActivity(new Intent(root.getContext(), MortgageProgramActivity.class)
                .putExtra("name", mode)
                .putExtra("projectCoast", projectCost)
                .putExtra("initialPayment", initialPayment)
                .putExtra("age", age)
                .putExtra("term", term));
    }

}