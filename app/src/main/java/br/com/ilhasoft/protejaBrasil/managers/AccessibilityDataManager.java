package br.com.ilhasoft.protejaBrasil.managers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;

/**
 * Created by john-mac on 4/18/16.
 */
public class AccessibilityDataManager {

    public static final int DEFAULT_SEX_OPTION_ID = 4;
    public static final int OTHER_LACK_ACCESSIBILITY_ID = 16;
    public static final int OTHER_LACK_ACCESSIBILITY_VIOLATION_ID = 156;

    public static List<ResponseObject> getInstitutionTypes(Context context) {
        List<ResponseObject> institutionTypes = new ArrayList<>();
        institutionTypes.add(new ResponseObject(27, context.getString(R.string.institution_type1)));
        institutionTypes.add(new ResponseObject(28, context.getString(R.string.institution_type2)));
        institutionTypes.add(new ResponseObject(29, context.getString(R.string.institution_type3)));
        return institutionTypes;
    }

    public static List<ResponseObject> getTransportTypes(Context context) {
        List<ResponseObject> transportTypes = new ArrayList<>();
        transportTypes.add(new ResponseObject(30, context.getString(R.string.transport_type1)));
        transportTypes.add(new ResponseObject(31, context.getString(R.string.transport_type2)));
        transportTypes.add(new ResponseObject(32, context.getString(R.string.transport_type3)));
        transportTypes.add(new ResponseObject(33, context.getString(R.string.transport_type4)));
        return transportTypes;
    }

    public static List<ResponseObject> getSexOptions(Context context) {
        List<ResponseObject> sexOptions = new ArrayList<>();
        sexOptions.add(new ResponseObject(1, context.getString(R.string.sex_option7)));
        sexOptions.add(new ResponseObject(2, context.getString(R.string.sex_option9)));
        sexOptions.add(new ResponseObject(3, context.getString(R.string.sex_option8)));
        sexOptions.add(new ResponseObject(4, context.getString(R.string.sex_option3)));
        return sexOptions;
    }

    public static List<ResponseObject> getViolationTypes(Context context) {
        List<ResponseObject> institutionTypes = new ArrayList<>();
        institutionTypes.add(new ResponseObject(14, context.getString(R.string.access_violation_type1)));
        institutionTypes.add(new ResponseObject(15, context.getString(R.string.access_violation_type2)));
        institutionTypes.add(new ResponseObject(16, context.getString(R.string.access_violation_type3)));
        return institutionTypes;
    }

    public static List<ResponseObject> getViolations(Context context) {
        List<ResponseObject> institutionTypes = new ArrayList<>();
        institutionTypes.add(new ResponseObject(140, context.getString(R.string.access_violation140)));
        institutionTypes.add(new ResponseObject(141, context.getString(R.string.access_violation141)));
        institutionTypes.add(new ResponseObject(142, context.getString(R.string.access_violation142)));
        institutionTypes.add(new ResponseObject(143, context.getString(R.string.access_violation143)));
        institutionTypes.add(new ResponseObject(144, context.getString(R.string.access_violation144)));
        institutionTypes.add(new ResponseObject(145, context.getString(R.string.access_violation145)));
        institutionTypes.add(new ResponseObject(146, context.getString(R.string.access_violation146)));
        institutionTypes.add(new ResponseObject(147, context.getString(R.string.access_violation147)));
        institutionTypes.add(new ResponseObject(148, context.getString(R.string.access_violation148)));
        institutionTypes.add(new ResponseObject(149, context.getString(R.string.access_violation149)));
        institutionTypes.add(new ResponseObject(150, context.getString(R.string.access_violation150)));
        institutionTypes.add(new ResponseObject(151, context.getString(R.string.access_violation151)));
        institutionTypes.add(new ResponseObject(152, context.getString(R.string.access_violation152)));
        institutionTypes.add(new ResponseObject(153, context.getString(R.string.access_violation153)));
        institutionTypes.add(new ResponseObject(154, context.getString(R.string.access_violation154)));
        institutionTypes.add(new ResponseObject(155, context.getString(R.string.access_violation155)));
        institutionTypes.add(new ResponseObject(156, context.getString(R.string.access_violation156)));
        return institutionTypes;
    }

}
