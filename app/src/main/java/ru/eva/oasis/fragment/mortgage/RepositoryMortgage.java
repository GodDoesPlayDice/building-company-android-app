package ru.eva.oasis.fragment.mortgage;

import ru.eva.oasis.interfaces.OnMortgageProgramsReceived;
import ru.eva.oasis.repository.Firebase;

class RepositoryMortgage implements ContractMortgage.Repository {
    @Override
    public void getMortgageProgram(String mode,
                                   int currentCoast,
                                   int currentInitialPayment,
                                   int age,
                                   int term,
                                   OnMortgageProgramsReceived onMortgageProgramsReceived) {
        Firebase.getInstance().getMortgageProgram(mode,
                currentCoast,
                currentInitialPayment,
                age,
                term,
                onMortgageProgramsReceived);
    }
}
