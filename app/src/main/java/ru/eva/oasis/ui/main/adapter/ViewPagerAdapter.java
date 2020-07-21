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
import ru.eva.oasis.interfaces.OnPagerItemClickListener;
import ru.eva.oasis.model.Banner;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private List<Banner> bannerList;
    private OnPagerItemClickListener onPagerItemClickListener;

    public ViewPagerAdapter(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager, parent, false);
        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Banner banner = bannerList.get(position);
        Picasso.get()
                .load(banner.getImageUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imageView);
        holder.title.setText(banner.getTitle());
    }


    @Override
    public int getItemCount() {
        return bannerList.size();
    }

    public void setOnPagerItemClickListener(OnPagerItemClickListener onPagerItemClickListener) {
        this.onPagerItemClickListener = onPagerItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView imageView;
        private final AppCompatTextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.titleTextView);
            itemView.setOnClickListener(v-> onPagerItemClickListener.onPagerClick(getAdapterPosition()));
        }
    }
}
