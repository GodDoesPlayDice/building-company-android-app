package ru.eva.oasis.fragment.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.enums.IndicatorSlideMode;
import com.zhpan.indicator.enums.IndicatorStyle;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.eva.oasis.R;
import ru.eva.oasis.activity.BuildingObjectActivity;
import ru.eva.oasis.activity.EventActivity;
import ru.eva.oasis.adapter.main.MainFragmentAdapter;
import ru.eva.oasis.adapter.main.ViewPagerAdapter;
import ru.eva.oasis.interfaces.OnBannersReceived;
import ru.eva.oasis.interfaces.OnItemClickListener;
import ru.eva.oasis.interfaces.OnPagerItemClickListener;
import ru.eva.oasis.model.Banner;
import ru.eva.oasis.model.BuildingObject;
import ru.eva.oasis.repository.Firebase;
import ru.eva.oasis.repository.Storage;

public class MainFragment extends Fragment implements OnPagerItemClickListener, OnItemClickListener, OnBannersReceived {
    private List<Banner> bannerList;
    private List<BuildingObject> buildingObjectList;
    private View root;
    private ViewPager2 viewPager2;
    private Disposable disposable;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        viewPager2 = root.findViewById(R.id.viewPager2);
        Firebase.getInstance().getBanners(this);

        IndicatorView indicatorView = root.findViewById(R.id.indicator_view);
        indicatorView
                .setSliderColor(root.getResources().getColor(R.color.gray), root.getResources().getColor(R.color.white))
                .setSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorStyle(IndicatorStyle.DASH)
                .setupWithViewPager(viewPager2);
        autoScrollingViewPager(viewPager2);

        buildingObjectList = Storage.getInstance().getBuildingObjects();
        MainFragmentAdapter adapter = new MainFragmentAdapter(buildingObjectList);
        adapter.setOnClickListener(this);
        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void autoScrollingViewPager(ViewPager2 viewPager2) {
        Observable<Long> values = Observable.interval(4000, TimeUnit.MILLISECONDS);
        disposable = values
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        (Long x) ->
                        {
                            int nextItem = viewPager2.getCurrentItem() + 1 == bannerList.size() ? 0 : viewPager2.getCurrentItem() + 1;
                            viewPager2.setCurrentItem(nextItem);
                        },
                        e -> Toast.makeText(root.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public void onPagerClick(int position) {
        startActivity(new Intent(root.getContext(), EventActivity.class)
                .putExtra("id", bannerList.get(position).getId()+""));
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(root.getContext(), BuildingObjectActivity.class)
                .putExtra("id", buildingObjectList.get(position).getId()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onResponse(List<Banner> bannerList) {
        this.bannerList = bannerList;

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(bannerList);
        viewPagerAdapter.setOnPagerItemClickListener(this);
        viewPager2.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(root.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}