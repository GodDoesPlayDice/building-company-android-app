package ru.eva.oasis.fragment.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.eva.oasis.R;

public class NewsFragment extends Fragment {

//    private NewsViewModel newsViewModel;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        root = inflater.inflate(R.layout.fragment_news, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        newsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}