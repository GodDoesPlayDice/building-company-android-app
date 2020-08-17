package ru.eva.oasis.fragment.mortgage;

import java.util.List;

import ru.eva.oasis.interfaces.OnMortgageProgramsReceived;
import ru.eva.oasis.model.MortgageProgram;

class ContractMortgage {
    interface View {
        void startMortgageActivity(String mode,
                                   int projectCost,
                                   int initialPayment,
                                   int age,
                                   int term);

        void setInitialPaymentProgress(int progress);

        void setProjectCoastText(String projectCostText);

        void setAdapter(List<MortgageProgram> programList);

        void showToast(String message);

        void setInitialPaymentText(String text);

        void setAgeEditTextError(String error);

        void setTermEditTextError(String error);
    }

    interface Presenter {
        void getMortgageProgram(String mortgageModeText,
                                String projectCoastTv,
                                String initialPaymentTv,
                                String ageTv,
                                String termTv);

        void startMortgageActivity(String mortgageModeText,
                                   String projectCoastTv,
                                   String initialPaymentTv,
                                   String ageTv,
                                   String termTv);

        void onCostProgressChanged(int progress, String initialPaymentTv);

        void onPaymentProgressChanged(int progress);
    }

    interface Repository {
        void getMortgageProgram(String mode,
                                int currentCoast,
                                int currentInitialPayment,
                                int age,
                                int term,
                                OnMortgageProgramsReceived onMortgageProgramsReceived);
    }
}
