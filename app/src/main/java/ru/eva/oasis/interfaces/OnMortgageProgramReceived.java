package ru.eva.oasis.interfaces;

import ru.eva.oasis.model.MortgageProgram;

public interface OnMortgageProgramReceived {
    void onResponse(MortgageProgram program);
    void onFailure(String message);
}
