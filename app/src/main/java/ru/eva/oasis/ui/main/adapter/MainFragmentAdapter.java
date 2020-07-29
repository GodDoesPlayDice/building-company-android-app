package ru.eva.oasis.ui.main.adapter;

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
import ru.eva.oasis.interfaces.OnRecyclerItemClickListener;
import ru.eva.oasis.model.BuildingObject;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private List<BuildingObject> buildingObjectList;

    public MainFragmentAdapter(List<BuildingObject> buildingObjectList) {
        this.buildingObjectList = buildingObjectList;
    }

    public void setOnClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new MainFragmentAdapter.ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BuildingObject object = buildingObjectList.get(position);
        Picasso.get()
                .load(object.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imageView);
        holder.title.setText(object.getTitle());
        holder.subtitle.setText(object.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return buildingObjectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView imageView;
        private final AppCompatTextView title, subtitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.titleTextView);
            subtitle = itemView.findViewById(R.id.subtitleTextView);
            itemView.setOnClickListener(v-> onRecyclerItemClickListener.onRecyclerItemClick(getAdapterPosition()));
        }
    }
}
