package ru.eva.oasis.repository;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.eva.oasis.interfaces.OnBannerReceived;
import ru.eva.oasis.interfaces.OnBannersReceived;
import ru.eva.oasis.interfaces.OnMortgageProgramReceived;
import ru.eva.oasis.interfaces.OnMortgageProgramsReceived;
import ru.eva.oasis.model.Banner;
import ru.eva.oasis.model.MortgageProgram;

public class Firebase {
    private static volatile Firebase instance;

    public static Firebase getInstance() {
        if (instance != null)
            return instance;
        return new Firebase();
    }

    public void getMortgageProgram(String mode, OnMortgageProgramsReceived onMortgageProgramsReceived) {
        Gson gson = new Gson();
        List<MortgageProgram> mortgageList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O2").child("mortgage");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    MortgageProgram mortgageProgram = gson.fromJson(gson.toJson(ds.getValue()), MortgageProgram.class);
                    if (mortgageProgram.getCalculationMode().equals(mode)) {
                        mortgageList.add(mortgageProgram);
                    }
                }
                onMortgageProgramsReceived.onResponse(mortgageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onMortgageProgramsReceived.onFailure(error.getMessage());
            }
        });
    }

    public void getMortgageProgramById(String id, OnMortgageProgramReceived onMortgageProgramReceived) {
        Gson gson = new Gson();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O2").child("mortgage").child((id));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onMortgageProgramReceived.onResponse(gson.fromJson(gson.toJson(snapshot.getValue()), MortgageProgram.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onMortgageProgramReceived.onFailure(error.getMessage());
            }
        });
    }

    public void getMortgageProgram(String mode,
                                   int currentCoast,
                                   int currentInitialPayment,
                                   int age,
                                   int term,
                                   OnMortgageProgramsReceived onMortgageProgramsReceived) {
        Gson gson = new Gson();
        List<MortgageProgram> mortgageList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O2").child("mortgage");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String json = gson.toJson(ds.getValue());
                    if(json != null && !json.equals("")) {
                        MortgageProgram mortgageProgram = gson.fromJson(json, MortgageProgram.class);
                        if (mortgageProgram.getCalculationMode().equals(mode) &&
                                mortgageProgram.getMinObjectCost() <= currentCoast &&
                                mortgageProgram.getMaxObjectCost() >= currentCoast &&
                                mortgageProgram.getMinInitialPayment() <= currentInitialPayment &&
                                mortgageProgram.getMaxInitialPayment() >= currentInitialPayment &&
                                mortgageProgram.getMinAge() <= age &&
                                mortgageProgram.getMaxAge() >= age &&
                                mortgageProgram.getMinMortgageTerm() <= term &&
                                mortgageProgram.getMaxMortgageTerm() >= term) {
                            mortgageList.add(mortgageProgram);
                        }
                    }
                }
                onMortgageProgramsReceived.onResponse(mortgageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onMortgageProgramsReceived.onFailure(error.getMessage());
            }
        });
    }

    public void getBanners(OnBannersReceived onBannersReceived) {
        Gson gson = new Gson();
        List<Banner> bannerList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O2").child("banner");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Banner banner = gson.fromJson(gson.toJson(ds.getValue()), Banner.class);
                    bannerList.add(banner);
                }
                onBannersReceived.onResponse(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onBannersReceived.onFailure(error.getMessage());
            }
        });
    }

    public void getBannerById(String id, OnBannerReceived onBannerReceived) {
        Gson gson = new Gson();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("O2").child("banner").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onBannerReceived.onResponse(gson.fromJson(gson.toJson(snapshot.getValue()), Banner.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onBannerReceived.onFailure(error.getMessage());
            }
        });
    }
}
