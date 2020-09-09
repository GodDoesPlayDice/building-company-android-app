package ru.eva.oasis.interfaces;

import java.util.List;

import ru.eva.oasis.model.Banner;

public interface OnBannersReceived {
    void onResponse(List<Banner> bannerList);
    void onFailure(String message);
}
