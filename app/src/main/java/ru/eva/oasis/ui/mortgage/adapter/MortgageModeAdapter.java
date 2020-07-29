package ru.eva.oasis.ui.mortgage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.eva.oasis.R;
import ru.eva.oasis.interfaces.OnBottomSheetItemClick;
import ru.eva.oasis.model.MortgageMode;

public class MortgageModeAdapter extends RecyclerView.Adapter<MortgageModeAdapter.ViewHolder> {

    private List<MortgageMode> mortgageModeList;
    private OnBottomSheetItemClick onBottomSheetItemClick;

    public MortgageModeAdapter(List<MortgageMode> mortgageModeList) {
        this.mortgageModeList = mortgageModeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mortgage_mode, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MortgageMode mortgageMode = mortgageModeList.get(position);
        holder.mortgageModeTextView.setText(mortgageMode.getMode());
        if(mortgageMode.isSelected())
            holder.checkedImageView.setVisibility(View.VISIBLE);
        else
            holder.checkedImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mortgageModeList.size();
    }

    public void setOnRecyclerItemClickListener(OnBottomSheetItemClick onBottomSheetItemClick) {
        this.onBottomSheetItemClick = onBottomSheetItemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView checkedImageView;
        private final AppCompatTextView mortgageModeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkedImageView = itemView.findViewById(R.id.checked_image_view);
            mortgageModeTextView = itemView.findViewById(R.id.mortgage_mode_text_view);
            itemView.setOnClickListener(v->{
                onBottomSheetItemClick.onClick(mortgageModeList.get(getAdapterPosition()).getMode());
                for (MortgageMode mode: mortgageModeList) {
                    mode.setSelected(false);
                }
                mortgageModeList.get(getAdapterPosition()).setSelected(true);
                notifyDataSetChanged();
            });
        }
    }
}
