package br.com.ilhasoft.protejaBrasil.managers;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.ilhasoft.protejaBrasil.R;
import br.com.ilhasoft.protejaBrasil.model.ResponseObject;

/**
 * Created by johncordeiro on 19/10/15.
 */
public class DefaultDataManager {

    public static final int DEFAULT_GENDER_ID = 3;
    public static final int DEFAULT_SEX_OPTION_ID = 3;
    public static final int DEFAULT_ETHNICITY = 6;

    public static List<ResponseObject> getRacialSubtypes(Context context) {
        List<ResponseObject> places = new ArrayList<>();
        places.add(new ResponseObject(1, context.getString(R.string.racial_subtype1), "Comunidades Quilombolas"));
        places.add(new ResponseObject(2, context.getString(R.string.racial_subtype2), "Comunidades de Matriz Africana"));
        places.add(new ResponseObject(3, context.getString(R.string.racial_subtype3), "Comunidades de Etnia Cigana"));
        places.add(new ResponseObject(4, context.getString(R.string.racial_subtype4), "Mulher Negra"));
        places.add(new ResponseObject(5, context.getString(R.string.racial_subtype5), "Juventudade Negra"));
        places.add(new ResponseObject(6, context.getString(R.string.racial_subtype6), "População Negra em Geral"));
        return places;
    }

    public static List<ResponseObject> getViolationPlaces(Context context) {
        List<ResponseObject> places = new ArrayList<>();
        places.add(new ResponseObject(1, context.getString(R.string.place1), "Casa"));
        places.add(new ResponseObject(2, context.getString(R.string.place2), "Rua"));
        places.add(new ResponseObject(3, context.getString(R.string.place3), "Local de trabalho"));
        places.add(new ResponseObject(4, context.getString(R.string.place4), "Ônibus"));
        places.add(new ResponseObject(13, context.getString(R.string.place13), "Escola"));
        places.add(new ResponseObject(14, context.getString(R.string.place14), "Unidade Prisional - Presídio"));
        places.add(new ResponseObject(15, context.getString(R.string.place15), "Albergue"));
        places.add(new ResponseObject(16, context.getString(R.string.place16), "Instituição de Longa Permanência para Idosos - ILPI"));
        places.add(new ResponseObject(17, context.getString(R.string.place17), "Igreja"));
        places.add(new ResponseObject(18, context.getString(R.string.place18), "Delegacia de Polícia"));
        places.add(new ResponseObject(19, context.getString(R.string.place19), "Delegacia de Polícia como Unidade Prisional"));
        places.add(new ResponseObject(20, context.getString(R.string.place20), "Unidade Prisional - Cadeia Pública"));
        places.add(new ResponseObject(21, context.getString(R.string.place21), "Unidade de Medida Sócio Educativa"));
        places.add(new ResponseObject(22, context.getString(R.string.place22), "Medida de Segurança - Manicômio Judicial"));
        places.add(new ResponseObject(23, context.getString(R.string.place23), "Manicômio/Hospital Psiquiátrico/Casa de Saúde"));
        places.add(new ResponseObject(24, context.getString(R.string.place24), "Casa do Suspeito"));
        places.add(new ResponseObject(25, context.getString(R.string.place25), "Casa da Vítima"));
        places.add(new ResponseObject(26, context.getString(R.string.place26), "Hospital"));
        places.add(new ResponseObject(5, context.getString(R.string.place5), "Outros"));
        return places;
    }

    public static List<ResponseObject> getFrequencies(Context context) {
        List<ResponseObject> frequencies = new ArrayList<>();
        frequencies.add(new ResponseObject(8, context.getString(R.string.frequency8), "Única vez"));
        frequencies.add(new ResponseObject(1, context.getString(R.string.frequency1), "Diariamente"));
        frequencies.add(new ResponseObject(3, context.getString(R.string.frequency3), "Toda manhã"));
        frequencies.add(new ResponseObject(4, context.getString(R.string.frequency4), "Toda tarde"));
        frequencies.add(new ResponseObject(2, context.getString(R.string.frequency2), "Semanalmente"));
        frequencies.add(new ResponseObject(9, context.getString(R.string.frequency9), "Quinzenalmente"));
        frequencies.add(new ResponseObject(7, context.getString(R.string.frequency7), "Mensalmente"));
        frequencies.add(new ResponseObject(10, context.getString(R.string.frequency10), "Ocasionalmente"));
        frequencies.add(new ResponseObject(5, context.getString(R.string.frequency5), "Não informado"));
        return frequencies;
    }

    public static List<ResponseObject> getAgeGroups(Context context) {
        List<ResponseObject> ageGroups = new ArrayList<>();
        ageGroups.add(new ResponseObject(18, context.getString(R.string.age_group18), "Nascituro"));
        ageGroups.add(new ResponseObject(19, context.getString(R.string.age_group19), "Recém-nascido"));
        ageGroups.add(new ResponseObject(20, context.getString(R.string.age_group20), "0 a 3 anos"));
        ageGroups.add(new ResponseObject(21, context.getString(R.string.age_group21), "4 a 7 anos"));
        ageGroups.add(new ResponseObject(22, context.getString(R.string.age_group22), "8 a 11 anos"));
        ageGroups.add(new ResponseObject(23, context.getString(R.string.age_group23), "12 a 14 anos"));
        ageGroups.add(new ResponseObject(24, context.getString(R.string.age_group24), "15 a 17 anos"));
        ageGroups.add(new ResponseObject(25, context.getString(R.string.age_group25), "17 a 19 anos"));
        ageGroups.add(new ResponseObject(3, context.getString(R.string.age_group3), "18 a 24 anos"));
        ageGroups.add(new ResponseObject(4, context.getString(R.string.age_group4), "25 a 30 anos"));
        ageGroups.add(new ResponseObject(5, context.getString(R.string.age_group5), "31 a 35 anos"));
        ageGroups.add(new ResponseObject(6, context.getString(R.string.age_group6), "36 a 40 anos"));
        ageGroups.add(new ResponseObject(7, context.getString(R.string.age_group7), "41 a 45 anos"));
        ageGroups.add(new ResponseObject(8, context.getString(R.string.age_group8), "46 a 50 anos"));
        ageGroups.add(new ResponseObject(9, context.getString(R.string.age_group9), "51 a 55 anos"));
        ageGroups.add(new ResponseObject(10, context.getString(R.string.age_group10), "56 a 60 anos"));
        ageGroups.add(new ResponseObject(11, context.getString(R.string.age_group11), "61 a 65 anos"));
        ageGroups.add(new ResponseObject(12, context.getString(R.string.age_group12), "66 a 70 anos"));
        ageGroups.add(new ResponseObject(13, context.getString(R.string.age_group13), "71 a 75 anos"));
        ageGroups.add(new ResponseObject(14, context.getString(R.string.age_group14), "76 a 80 anos"));
        ageGroups.add(new ResponseObject(15, context.getString(R.string.age_group15), "81 a 85 anos"));
        ageGroups.add(new ResponseObject(16, context.getString(R.string.age_group16), "85 a 90 anos"));
        ageGroups.add(new ResponseObject(17, context.getString(R.string.age_group17), "91 anos ou mais"));
        return ageGroups;
    }

    public static List<ResponseObject> getEthnicities(Context context) {
        List<ResponseObject> ethnicities = new ArrayList<>();
        ethnicities.add(new ResponseObject(3, context.getString(R.string.ethnicity3), "Amarela"));
        ethnicities.add(new ResponseObject(1, context.getString(R.string.ethnicity1), "Branca"));
        ethnicities.add(new ResponseObject(5, context.getString(R.string.ethnicity5), "Indígena"));
        ethnicities.add(new ResponseObject(6, context.getString(R.string.ethnicity6), "Não informado"));
        ethnicities.add(new ResponseObject(4, context.getString(R.string.ethnicity4), "Parda"));
        ethnicities.add(new ResponseObject(2, context.getString(R.string.ethnicity2), "Preta"));
        return ethnicities;
    }

    public static List<ResponseObject> getGenders(Context context) {
        List<ResponseObject> genders = new ArrayList<>();
        genders.add(new ResponseObject(1, context.getString(R.string.sex1), "Masculino"));
        genders.add(new ResponseObject(2, context.getString(R.string.sex2), "Feminino"));
        genders.add(new ResponseObject(3, context.getString(R.string.sex3), "Não informado"));
        return genders;
    }

    public static List<ResponseObject> getSexOptions(Context context) {
        List<ResponseObject> sexOptions = new ArrayList<>();
        sexOptions.add(new ResponseObject(7, context.getString(R.string.sex_option7), "Homossexual"));
        sexOptions.add(new ResponseObject(9, context.getString(R.string.sex_option9), "Heterossexual"));
        sexOptions.add(new ResponseObject(8, context.getString(R.string.sex_option8), "Bissexual"));
        sexOptions.add(new ResponseObject(3, context.getString(R.string.sex_option3), "Não informado"));
        return sexOptions;
    }

    public static List<ResponseObject> getInternetCrimeOptions(Context context) {
        List<ResponseObject> internetCrimeOptions = new ArrayList<>();
        internetCrimeOptions.add(new ResponseObject(1, context.getString(R.string.internet_option_1)));
        internetCrimeOptions.add(new ResponseObject(2, context.getString(R.string.internet_option_2)));
        internetCrimeOptions.add(new ResponseObject(3, context.getString(R.string.internet_option_3)));
        internetCrimeOptions.add(new ResponseObject(4, context.getString(R.string.internet_option_4)));
        internetCrimeOptions.add(new ResponseObject(5, context.getString(R.string.internet_option_5)));
        internetCrimeOptions.add(new ResponseObject(6, context.getString(R.string.internet_option_6)));
        internetCrimeOptions.add(new ResponseObject(7, context.getString(R.string.internet_option_7)));
        internetCrimeOptions.add(new ResponseObject(8, context.getString(R.string.internet_option_8)));
        internetCrimeOptions.add(new ResponseObject(9, context.getString(R.string.internet_option_9)));
        return internetCrimeOptions;
    }

    public static List<ResponseObject> getViolenceAgainsWomenOptions(Context context) {
        List<ResponseObject> options = new ArrayList<>();
        options.add(new ResponseObject(1, context.getString(R.string.domestic_and_familiar_violence)));
        options.add(new ResponseObject(2, context.getString(R.string.intimidation)));
        options.add(new ResponseObject(3, context.getString(R.string.feminicide)));
        options.add(new ResponseObject(4, context.getString(R.string.women_trade)));
        options.add(new ResponseObject(5, context.getString(R.string.false_imprisonment)));
        options.add(new ResponseObject(6, context.getString(R.string.violence_against_religious_diversity)));
        options.add(new ResponseObject(7, context.getString(R.string.violence_on_sports)));
        options.add(new ResponseObject(8, context.getString(R.string.homicide)));
        options.add(new ResponseObject(9, context.getString(R.string.institutional_violence)));
        options.add(new ResponseObject(10, context.getString(R.string.physical_violence)));
        options.add(new ResponseObject(11, context.getString(R.string.moral_violence)));
        options.add(new ResponseObject(12, context.getString(R.string.police_violence)));
        options.add(new ResponseObject(13, context.getString(R.string.obstetrical_violence)));
        options.add(new ResponseObject(14, context.getString(R.string.sexual_violence)));
        options.add(new ResponseObject(15, context.getString(R.string.virtual_violence)));
        options.add(new ResponseObject(16, context.getString(R.string.slave_work)));
        options.add(new ResponseObject(17, context.getString(R.string.other_entries)));
        return options;
    }
}
