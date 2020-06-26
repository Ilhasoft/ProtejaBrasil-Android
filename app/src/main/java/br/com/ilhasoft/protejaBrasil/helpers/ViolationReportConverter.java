package br.com.ilhasoft.protejaBrasil.helpers;

import br.com.ilhasoft.protejaBrasil.model.City;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;
import br.com.ilhasoft.protejaBrasil.model.State;
import br.com.ilhasoft.protejaBrasil.model.Theme;
import br.com.ilhasoft.protejaBrasil.model.ViolationReport;
import br.com.ilhasoft.protejaBrasil.model.view.ViolationReportViewModel;
import br.com.ilhasoft.support.databinding.bindings.BindableField;

/**
 * Created by johncordeiro on 20/10/15.
 */
public class ViolationReportConverter {

    public ViolationReport violationReportFromViewModel(ViolationReportViewModel viewModel) {
        ViolationReport violationReport = new ViolationReport();
        violationReport.setTheme(((Theme) viewModel.reportTheme.get()));
        if(viewModel.subtypes.exists()) {
            ResponseObject subtype = (ResponseObject) viewModel.subtypes.get();
            violationReport.setSubtypeId(subtype.getId());
            violationReport.setSubtypeDescription(subtype.getDisplayName());
        }
        violationReport.setVictimLocation(viewModel.victimLocation.get());
        violationReport.setViolationLocal(getNameFromResponseObject(viewModel.violationPlace));
        violationReport.setFrequency(getNameFromResponseObject(viewModel.violationTime));
        violationReport.setInstitutionActivated(viewModel.institutionActivated.get());
        violationReport.setViolationDescription(viewModel.violationDescription.get());
        violationReport.setVictimName(viewModel.victimName.get());
        violationReport.setVictimAddressCity((City) viewModel.victimAddress.city.get());
        violationReport.setVictimAddressState((State)viewModel.victimAddress.state.get());
        violationReport.setVictimAddressStreet(viewModel.victimAddress.address.get());
        violationReport.setVictimSex(getNameFromResponseObject(viewModel.victimGender));
        violationReport.setVictimSexOption(getNameFromResponseObject(viewModel.victimSexOption));
        violationReport.setVictimAgeGroup(getNameFromResponseObject(viewModel.victimAgeGroup));
        violationReport.setVictimEthnicity(getNameFromResponseObject(viewModel.victimEthnicity));
        violationReport.setOffenderType(getIdFromResponseObject(viewModel.offenderType));
        violationReport.setOffenderName(viewModel.offenderName.get());
        violationReport.setOffenderAddressCity((City) viewModel.offenderAddress.city.get());
        violationReport.setOffenderAddressState((State) viewModel.offenderAddress.state.get());
        violationReport.setOffenderAddressStreet(viewModel.offenderAddress.address.get());
        violationReport.setOffenderSex(getNameFromResponseObject(viewModel.offenderGender));
        violationReport.setOffenderSexOption(getNameFromResponseObject(viewModel.offenderSexOption));
        violationReport.setOffenderAgeGroup(getNameFromResponseObject(viewModel.offenderAgeGroup));
        violationReport.setOffenderEthnicity(getNameFromResponseObject(viewModel.offenderEthnicity));
        return violationReport;
    }

    private Integer getIdFromResponseObject(BindableField bindableField) {
        if(bindableField != null && bindableField.exists()) {
            ResponseObject responseObject = ((ResponseObject)bindableField.get());
            return responseObject.getId();
        }
        return null;
    }

    private String getNameFromResponseObject(BindableField bindableField) {
        if(bindableField != null && bindableField.exists()) {
            ResponseObject responseObject = ((ResponseObject)bindableField.get());
            if(responseObject.getKeyName() != null && !responseObject.getKeyName().isEmpty()) {
                return responseObject.getKeyName();
            } else {
                return "Não informado";
            }
        }
        return null;
    }

}
