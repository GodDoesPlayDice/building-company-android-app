package ru.eva.oasis.adapter.program;

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
import ru.eva.oasis.interfaces.OnItemClickListener;
import ru.eva.oasis.model.MortgageProgram;
import ru.eva.oasis.model.Program;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<MortgageProgram> programList;
    public ProgramAdapter(List<MortgageProgram> programList) {
        this.programList = programList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_program_more, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MortgageProgram program = programList.get(position);
        holder.companyTextView.setText(program.getBankName());
        holder.textView.setText(program.getProgramName());
        holder.secondaryTextTextView.setText(program.getCalculationMode());
        Picasso.get()
                .load(program.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
            itemView.setOnClickListener(v-> onItemClickListener.onClick(programList.get(getAdapterPosition()).getId()));
        }
    }
}
