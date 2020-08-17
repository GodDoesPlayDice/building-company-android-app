package ru.eva.oasis.interfaces;

import java.util.List;

import ru.eva.oasis.model.MortgageProgram;

public interface OnMortgageProgramsReceived {
    void onResponse(List<MortgageProgram> programList);
    void onFailure(String message);
}
