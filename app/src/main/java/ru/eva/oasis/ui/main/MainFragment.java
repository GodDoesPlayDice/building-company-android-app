package ru.eva.oasis.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.List;

import ru.eva.oasis.BuildingObjectActivity;
import ru.eva.oasis.EventActivity;
import ru.eva.oasis.MainActivity;
import ru.eva.oasis.R;
import ru.eva.oasis.interfaces.OnPagerItemClickListener;
import ru.eva.oasis.interfaces.OnRecyclerItemClickListener;
import ru.eva.oasis.model.Banner;
import ru.eva.oasis.model.BuildingObject;
import ru.eva.oasis.repository.Storage;
import ru.eva.oasis.ui.main.adapter.MainFragmentAdapter;
import ru.eva.oasis.ui.main.adapter.ViewPagerAdapter;

public class MainFragment extends Fragment implements OnPagerItemClickListener, OnRecyclerItemClickListener {
    private List<Banner> bannerList;
    private List<BuildingObject> buildingObjectList;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        ViewPager2 viewPager2 = root.findViewById(R.id.viewPager2);
        bannerList = Storage.getInstance().getBannerList();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(bannerList);
        viewPagerAdapter.setOnPagerItemClickListener(this);
        viewPager2.setAdapter(viewPagerAdapter);

        IndicatorView indicatorView = root.findViewById(R.id.indicator_view);
        indicatorView
                .setSliderColor(root.getResources().getColor(R.color.gray), root.getResources().getColor(R.color.white))
                .setSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorStyle(IndicatorStyle.DASH)
                .setupWithViewPager(viewPager2);

        buildingObjectList = Storage.getInstance().getBuildingObjects();
        MainFragmentAdapter adapter = new MainFragmentAdapter(buildingObjectList);
        adapter.setOnClickListener(this);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onPagerClick(int position) {
        startActivity(new Intent(root.getContext(), EventActivity.class)
                .putExtra("id", bannerList.get(position).getId()));
    }

    @Override
    public void onRecyclerItemClick(int position) {
        startActivity(new Intent(root.getContext(), BuildingObjectActivity.class)
                .putExtra("id", buildingObjectList.get(position).getId()));
    }
}