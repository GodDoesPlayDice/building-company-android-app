package ru.eva.oasis.fragment.mortgage;

import java.util.List;

import ru.eva.oasis.interfaces.OnMortgageProgramsReceived;
import ru.eva.oasis.model.MortgageProgram;

class PresenterMortgage implements ContractMortgage.Presenter, OnMortgageProgramsReceived {
    private ContractMortgage.View mView;
    private ContractMortgage.Repository mRepository;

    private double maxInitialPayment, minInitialPayment;
    private double minCost = 45000;
    private double maxCost = 10000000;

    PresenterMortgage(ContractMortgage.View mView) {
        this.mView = mView;
        mRepository = new RepositoryMortgage();
    }

    @Override
    public void getMortgageProgram(String mortgageModeText,
                                   String projectCoastTv,
                                   String initialPaymentTv,
                                   String ageTv,
                                   String termTv) {
        if (checkInputFields(ageTv, termTv))
            mRepository.getMortgageProgram(mortgageModeText,
                    Integer.parseInt(projectCoastTv),
                    Integer.parseInt(initialPaymentTv),
                    Integer.parseInt(ageTv),
                    Integer.parseInt(termTv),
                    this);
    }

    @Override
    public void startMortgageActivity(String mortgageModeText,
                                      String projectCoastTv,
                                      String initialPaymentTv,
                                      String ageTv,
                                      String termTv) {
        mView.startMortgageActivity(mortgageModeText,
                Integer.parseInt(projectCoastTv),
                Integer.parseInt(initialPaymentTv),
                Integer.parseInt(ageTv),
                Integer.parseInt(termTv));
    }

    @Override
    public void onCostProgressChanged(int progress, String initialPaymentTv) {
        mView.setProjectCoastText(getProjectCostText(progress));

        minInitialPayment = getProjectCost(progress) / 10;
        maxInitialPayment = getProjectCost(progress) / 10 * 9;
        if (minInitialPayment > Double.parseDouble(initialPaymentTv)) {
            mView.setInitialPaymentText((int) minInitialPayment + "");
        }
        if (maxInitialPayment < Double.parseDouble(initialPaymentTv)) {
            mView.setInitialPaymentText((int) maxInitialPayment + "");
        }
        getInitialPaymentProgress(initialPaymentTv);
    }

    @Override
    public void onPaymentProgressChanged(int progress) {
        double calc = minInitialPayment + ((double) progress / 100) * (maxInitialPayment - minInitialPayment);
        String text = (int) calc + "";
        mView.setInitialPaymentText(text);
    }

    public void getInitialPaymentProgress(String initialPaymentTv) {
        double progress = (Double.parseDouble(initialPaymentTv) - minInitialPayment) / (maxInitialPayment - minInitialPayment) * 100;
        mView.setInitialPaymentProgress((int) progress);
    }


    @Override
    public void onResponse(List<MortgageProgram> programList) {
        mView.setAdapter(programList);
    }

    @Override
    public void onFailure(String message) {
        mView.showToast(message);
    }

    private boolean checkInputFields(String age, String term) {
        if (!age.equals("") && !term.equals("")) {
            return true;
        } else {
            if (age.equals("")) {
                mView.setAgeEditTextError("Заполните поле");
            }
            if (term.equals("")) {
                mView.setTermEditTextError("Заполните поле");
            }
        }
        return false;
    }

    private String getProjectCostText(int progress) {
        return (int) getProjectCost(progress) + "";
    }

    private double getProjectCost(int progress) {
        return minCost + ((double) progress / 100) * (maxCost - minCost);
    }

}
