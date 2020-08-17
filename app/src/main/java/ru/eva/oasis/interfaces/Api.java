package ru.eva.oasis.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("/mortgage/mode")
    Call<String> getMortgage();
}
