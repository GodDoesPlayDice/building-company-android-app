package ru.eva.oasis.repository;

import java.util.ArrayList;
import java.util.List;

import ru.eva.oasis.model.Banner;
import ru.eva.oasis.model.BuildingObject;
import ru.eva.oasis.model.MortgageMode;
import ru.eva.oasis.model.Program;

public class Storage {
    private static volatile Storage instance;

    public static Storage getInstance() {
        if (instance != null)
            return instance;
        return new Storage();
    }

    public List<Program> getProgramList() {
        List<Program> programList = new ArrayList<>();
        Program program = new Program();
        program.setCompany("Себрбанк");
        program.setText("Акция на новостройки");
        program.setSecondaryText("От 20%");
        program.setId(1);
        program.setImageUrl("https://жкоазискрд.рф/img/sale/web4.jpg");
        program.setDescription("Описание программы");
        for (int i = 0; i < 20; i++) {
            programList.add(program);
        }
        return programList;
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
        String[] modeName = {"Стандартный режим расчета", "Военная ипотека", "Семейная ипотека", "По двум документам"};
        for (int i = 0; i < modeName.length; i++) {
            MortgageMode mortgageMode = new MortgageMode();
            mortgageMode.setMode(modeName[i]);
            mortgageMode.setSelected(false);
            if (mortgageMode.getMode().equals(name))
                mortgageMode.setSelected(true);
            mortgageModeList.add(mortgageMode);
        }
        return mortgageModeList;
    }
}
