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
import ru.eva.oasis.helper.Convert;
import ru.eva.oasis.interfaces.OnBottomSheetItemClick;
import ru.eva.oasis.interfaces.OnItemClickListener;
import ru.eva.oasis.interfaces.OnMortgageProgramsReceived;
import ru.eva.oasis.model.MortgageProgram;
import ru.eva.oasis.repository.Firebase;

public class MortgageFragment extends Fragment implements OnBottomSheetItemClick, OnItemClickListener, OnMortgageProgramsReceived, TextView.OnEditorActionListener {

    private View root;
    private TextInputEditText mortgageModeText;
    private BottomSheetFragment bottomSheetFragment;
    private double maxInitialPayment, minInitialPayment;
    private double minCost = 45000;
    private double maxCost = 1000000;
    private AppCompatTextView projectCoastTv;
    private AppCompatTextView initialPaymentTv;
    private TextInputEditText ageTv;
    private TextInputEditText termTv;
    private int[] ids;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mortgage, container, false);

        mortgageModeText = root.findViewById(R.id.mortgage_mode_text);
        AppCompatImageView mortgageModeImage = root.findViewById(R.id.mortgage_mode_image);
        mortgageModeText.setOnClickListener(v -> openBottomSheetFragment());

        mortgageModeImage.setOnClickListener(v -> openBottomSheetFragment());

        AppCompatTextView showAll = root.findViewById(R.id.show_all_text_view);
        showAll.setOnClickListener(v -> startMortgageProgramActivity());

        projectCoastTv = root.findViewById(R.id.project_coast_text_view);
        AppCompatSeekBar projectCoastSb = root.findViewById(R.id.project_coast_seekbar);
        initialPaymentTv = root.findViewById(R.id.intial_payment_text_view);
        AppCompatSeekBar initialPaymentSb = root.findViewById(R.id.intial_payment_seekbar);

        ageTv = root.findViewById(R.id.age_text_view);
        termTv = root.findViewById(R.id.term_text_view);

        Firebase.getInstance().getMortgageProgram("Стандартный режим расчета", this);

        ageTv.setOnEditorActionListener(this);
        termTv.setOnEditorActionListener(this);

        projectCoastSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                projectCoastTv.setText(getProjectCostText(progress));
                minInitialPayment = getProjectCost(progress) / 10;
                maxInitialPayment = getProjectCost(progress) / 10 * 9;
                if (minInitialPayment > Convert.getInstance().stringToDouble(initialPaymentTv)) {
                    initialPaymentTv.setText((int) minInitialPayment + "");
                }
                if (maxInitialPayment < Convert.getInstance().stringToDouble(initialPaymentTv)) {
                    initialPaymentTv.setText((int) maxInitialPayment + "");
                }
                initialPaymentSb.setProgress(calculateInitialPaymentProgress(Convert.getInstance().stringToDouble(initialPaymentTv)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getMortgageProgram(mortgageModeText.getText().toString());
            }
        });

        initialPaymentSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double calc = minInitialPayment + ((double) progress / 100) * (maxInitialPayment - minInitialPayment);
                String text = (int) calc + "";
                initialPaymentTv.setText(text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                getMortgageProgram(mortgageModeText.getText().toString());
            }
        });
        AppCompatButton submit = root.findViewById(R.id.submit_btn);
        submit.setOnClickListener(v -> {

        });
        return root;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            getMortgageProgram(mortgageModeText.getText().toString());
            return event == null || !event.isShiftPressed();
        }
        return false;
    }

    @Override
    public void onClick(String name) {
        mortgageModeText.setText(name);
        bottomSheetFragment.dismiss();
        getMortgageProgram(name);
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(root.getContext(), MortgageProgramDetailsActivity.class)
                .putExtra("id", position + ""));
    }

    @Override
    public void onResponse(List<MortgageProgram> programList) {
        MortgageProgramAdapter adapter = new MortgageProgramAdapter(programList);
        adapter.setOnItemClickListener(this);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        ids = new int[programList.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = programList.get(i).getId();
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void startMortgageProgramActivity() {
        if (checkInputFields())
        startActivity(new Intent(root.getContext(), MortgageProgramActivity.class)
                .putExtra("name", mortgageModeText.getText().toString())
                .putExtra("projectCoast", Convert.getInstance().stringToInt(projectCoastTv))
                .putExtra("initialPayment", Convert.getInstance().stringToInt(initialPaymentTv))
                .putExtra("age", Convert.getInstance().stringToInt(ageTv))
                .putExtra("term", Convert.getInstance().stringToInt(termTv)));
    }

    private void getMortgageProgram(String name) {
        if (checkInputFields())
            Firebase.getInstance().getMortgageProgram(name,
                    Convert.getInstance().stringToInt(projectCoastTv),
                    Convert.getInstance().stringToInt(initialPaymentTv),
                    Convert.getInstance().stringToInt(ageTv),
                    Convert.getInstance().stringToInt(termTv),
                    MortgageFragment.this);
    }

    private boolean checkInputFields() {
        if (!ageTv.getText().toString().equals("") && !termTv.getText().toString().equals("")) {
            return true;
        } else {
            if (ageTv.toString().equals("")) {
                ageTv.setError("Заполните поле");
                return false;
            }
            if (termTv.toString().equals(""))
                termTv.setError("Заполните поле");
            return false;
        }
    }

    private String getProjectCostText(int progress) {
        return (int) getProjectCost(progress) + "";
    }

    private double getProjectCost(int progress) {
        return minCost + ((double) progress / 100) * (maxCost - minCost);
    }

    private int calculateInitialPaymentProgress(double current) {
        double calc = (current - minInitialPayment) / (maxInitialPayment - minInitialPayment) * 100;
        return (int) calc;
    }

    private void openBottomSheetFragment() {
        bottomSheetFragment = BottomSheetFragment.newInstance(mortgageModeText.getText().toString());
        bottomSheetFragment.setOnItemClickListener(this);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
    }
}