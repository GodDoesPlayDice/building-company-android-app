package ru.eva.oasis.repository;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ru.eva.oasis.model.Banner;
import ru.eva.oasis.model.BuildingObject;
import ru.eva.oasis.model.MortgageMode;
import ru.eva.oasis.model.Programm;

public class Storage {
    private static volatile Storage instance;

    public static Storage getInstance() {
        if(instance != null)
            return instance;
        return new Storage();
    }

    public List<Programm> getProgramList() {
        List<Programm> programmList = new ArrayList<>();
        Programm programm = new Programm();
        programm.setCompany("Себрбанк");
        programm.setText("Акция на новостройки");
        programm.setSecondaryText("От 20%");
        programm.setId(1);
        programm.setImageUrl("https://жкоазискрд.рф/img/sale/web4.jpg");
        for (int i = 0; i < 20; i++) {
            programmList.add(programm);
        }
        return programmList;
    }

    public List<Banner> getBannerList() {
        List<Banner> banners;
        Banner banner = new Banner();
        banner.setImageUrl("https://avatars.mds.yandex.net/get-pdb/1532005/b620c481-8abf-426e-a9ff-cfaff2a71e8a/s1200");
        banner.setTitle("Имя объекта");
        banner.setId(7);
        banners = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            banners.add(banner);
        }
        return banners;
    }

    public List<BuildingObject> getBuildingObjects() {
        List<BuildingObject> buildingObjects;
        BuildingObject buildingObject = new BuildingObject();
        buildingObject.setImageUrl("https://avatars.mds.yandex.net/get-pdb/2491878/9997f5c6-c20c-45b0-9930-a3343b5a59a7/s1200");
        buildingObject.setTitle("Имя объекта");
        buildingObject.setSubtitle("Подзагловок");
        buildingObject.setId(7);
        buildingObjects = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            buildingObjects.add(buildingObject);
        }
        return buildingObjects;
    }

    public List<MortgageMode> getMortgageList(String name) {
        List<MortgageMode> mortgageModeList = new ArrayList<>();
        for(int i=0; i<5; i++) {
            MortgageMode mortgageMode = new MortgageMode();
            mortgageMode.setMode("Режим "+(i+1));
            mortgageMode.setSelected(false);
            if(mortgageMode.getMode().equals(name))
                mortgageMode.setSelected(true);
            mortgageModeList.add(mortgageMode);
        }
        return mortgageModeList;
    }
}
