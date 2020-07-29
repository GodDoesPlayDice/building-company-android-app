package ru.eva.oasis.ui.mortgage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.eva.oasis.R;
import ru.eva.oasis.model.Programm;

public class MortgageProgramAdapter extends RecyclerView.Adapter<MortgageProgramAdapter.ViewHolder> {
    private List<Programm> programList;
    public MortgageProgramAdapter(List<Programm> programList) {
        this.programList = programList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Programm programm = programList.get(position);
        holder.companyTextView.setText(programm.getCompany());
        holder.textView.setText(programm.getText());
        holder.secondaryTextTextView.setText(programm.getSecondaryText());
        Picasso.get()
                .load(programm.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatTextView companyTextView, textView, secondaryTextTextView;
        private final AppCompatImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyTextView = itemView.findViewById(R.id.company_text_view);
            textView = itemView.findViewById(R.id.text_view);
            secondaryTextTextView = itemView.findViewById(R.id.secondry_text_view);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
