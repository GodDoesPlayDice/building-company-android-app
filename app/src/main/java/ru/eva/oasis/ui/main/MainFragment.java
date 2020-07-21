package ru.eva.oasis.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.ArrayList;
import java.util.List;

import ru.eva.oasis.BuildingObjectActivity;
import ru.eva.oasis.EventActivity;
import ru.eva.oasis.R;
import ru.eva.oasis.interfaces.OnPagerItemClickListener;
import ru.eva.oasis.interfaces.OnRecyclerItemClickListener;
import ru.eva.oasis.model.Banner;
import ru.eva.oasis.model.BuildingObject;
import ru.eva.oasis.ui.main.adapter.MainFragmentAdapter;
import ru.eva.oasis.ui.main.adapter.ViewPagerAdapter;

public class MainFragment extends Fragment implements OnPagerItemClickListener, OnRecyclerItemClickListener {
    private List<Banner> bannerList;
    private List<BuildingObject> buildingObjectList;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        ViewPager2 viewPager2 = root.findViewById(R.id.viewPager2);
       bannerList = getBannerList();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(bannerList);
        viewPagerAdapter.setOnPagerItemClickListener(this);
        viewPager2.setAdapter(viewPagerAdapter);

        IndicatorView indicatorView = root.findViewById(R.id.indicator_view);
        indicatorView
                .setSliderColor(R.color.colorPrimary, R.color.colorPrimaryDark)
                .setSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setupWithViewPager(viewPager2);

        buildingObjectList = getBuildingObjects();
        MainFragmentAdapter adapter = new MainFragmentAdapter(buildingObjectList);
        adapter.setOnClickListener(this);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        recyclerView.setAdapter(adapter);

        return root;
    }

    private List<Banner> getBannerList() {
        List<Banner> banners;
        Banner banner = new Banner();
        banner.setImageUrl("https://жкоазискрд.рф/img/sale/web4.jpg");
        banner.setTitle("Выгода!");
        banner.setId(7);
        banners = new ArrayList<>();
        banners.add(banner);
        banners.add(banner);
        banners.add(banner);
        banners.add(banner);
        return banners;
    }

    private List<BuildingObject> getBuildingObjects() {
        List<BuildingObject> buildingObjects;
        BuildingObject buildingObject = new BuildingObject();
        buildingObject.setImageUrl("https://жкоазискрд.рф/img/sale/web4.jpg");
        buildingObject.setTitle("Выгода!");
        buildingObject.setId(7);
        buildingObjects = new ArrayList<>();
        buildingObjects.add(buildingObject);
        buildingObjects.add(buildingObject);
        buildingObjects.add(buildingObject);
        buildingObjects.add(buildingObject);
        return buildingObjects;
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