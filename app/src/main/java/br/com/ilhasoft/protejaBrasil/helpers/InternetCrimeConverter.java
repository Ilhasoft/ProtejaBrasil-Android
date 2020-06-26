package br.com.ilhasoft.protejaBrasil.helpers;

import br.com.ilhasoft.protejaBrasil.model.InternetCrime;
import br.com.ilhasoft.protejaBrasil.model.InternetViolation;
import br.com.ilhasoft.protejaBrasil.model.view.InternetCrimeViewModel;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class InternetCrimeConverter {

    public InternetCrime internetCrimeFromViewModel(InternetCrimeViewModel viewModel) {
        InternetCrime internetCrime = new InternetCrime();
        internetCrime.setViolationType(((InternetViolation) viewModel.violationType.get()));
        internetCrime.setDescription(viewModel.description.get());
        internetCrime.setWebsite(viewModel.website.get());
        return internetCrime;
    }

}
