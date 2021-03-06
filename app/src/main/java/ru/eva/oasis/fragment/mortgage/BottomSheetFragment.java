package ru.eva.oasis.fragment.mortgage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import ru.eva.oasis.R;
import ru.eva.oasis.interfaces.OnBottomSheetItemClick;
import ru.eva.oasis.model.MortgageMode;
import ru.eva.oasis.repository.Storage;
import ru.eva.oasis.adapter.mortgage.MortgageModeAdapter;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private OnBottomSheetItemClick onBottomSheetItemClick;
    private static final String ARG_PARAM1 = "name";
    private String name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        List<MortgageMode> mortgageModeList = Storage.getInstance().getMortgageList(name);

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        MortgageModeAdapter adapter = new MortgageModeAdapter(mortgageModeList);
        recyclerView.setAdapter(adapter);
        recyclerView.setNestedScrollingEnabled(false);
        adapter.setOnRecyclerItemClickListener(onBottomSheetItemClick);
        return root;
    }

    public static BottomSheetFragment newInstance(String name) {
        BottomSheetFragment fragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        }
    }

    public void setOnItemClickListener(OnBottomSheetItemClick onBottomSheetItemClick) {
        this.onBottomSheetItemClick = onBottomSheetItemClick;
    }
}