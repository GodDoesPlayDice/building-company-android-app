package ru.eva.oasis.interfaces;

import ru.eva.oasis.model.Banner;

public interface OnBannerReceived {
    void onResponse(Banner banner);

    void onFailure(String message);
}
