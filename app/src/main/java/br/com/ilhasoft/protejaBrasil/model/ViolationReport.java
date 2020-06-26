package br.com.ilhasoft.protejaBrasil.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by johncordeiro on 18/10/15.
 */
public class ViolationReport extends Report {

    public static int VIOLATION_AGAINST_WOMEN_ID = 774;

    private Theme theme;

    private Integer subtypeId;

    private String subtypeDescription;

    private String victimLocation;

    @SerializedName("violation_local")
    private String violationLocal;

    private String frequency;

    @SerializedName("institution_activated")
    private String institutionActivated;

    @SerializedName("violation_description")
    private String violationDescription;

    @SerializedName("victim_name")
    private String victimName;

    @SerializedName("victim_address_uf")
    private State victimAddressState;

    @SerializedName("victim_address_city")
    private City victimAddressCity;

    @SerializedName("victim_address_street")
    private String victimAddressStreet;

    @SerializedName("victim_sex")
    private String victimSex;

    @SerializedName("victim_sex_option")
    private String victimSexOption;

    @SerializedName("victim_age_group")
    private String victimAgeGroup;

    @SerializedName("victim_ethnicity")
    private String victimEthnicity;

    @SerializedName("offender_type")
    private Integer offenderType;

    @SerializedName("offender_name")
    private String offenderName;

    @SerializedName("offender_address_uf")
    private State offenderAddressState;

    @SerializedName("offender_address_city")
    private City offenderAddressCity;

    @SerializedName("offender_address_street")
    private String offenderAddressStreet;

    @SerializedName("offender_sex")
    private String offenderSex;

    @SerializedName("offender_sex_option")
    private String offenderSexOption;

    @SerializedName("offender_age_group")
    private String offenderAgeGroup;

    @SerializedName("offender_ethnicity")
    private String offenderEthnicity;

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Integer getSubtypeId() {
        return subtypeId;
    }

    public void setSubtypeId(Integer subtypeId) {
        this.subtypeId = subtypeId;
    }

    public String getSubtypeDescription() {
        return subtypeDescription;
    }

    public void setSubtypeDescription(String subtypeDescription) {
        this.subtypeDescription = subtypeDescription;
    }

    public String getVictimLocation() {
        return victimLocation;
    }

    public void setVictimLocation(String victimLocation) {
        this.victimLocation = victimLocation;
    }

    public String getViolationLocal() {
        return violationLocal;
    }

    public void setViolationLocal(String violationLocal) {
        this.violationLocal = violationLocal;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getInstitutionActivated() {
        return institutionActivated;
    }

    public void setInstitutionActivated(String institutionActivated) {
        this.institutionActivated = institutionActivated;
    }

    public String getViolationDescription() {
        return violationDescription;
    }

    public void setViolationDescription(String violationDescription) {
        this.violationDescription = violationDescription;
    }

    public String getVictimName() {
        return victimName;
    }

    public void setVictimName(String victimName) {
        this.victimName = victimName;
    }

    public State getVictimAddressState() {
        return victimAddressState;
    }

    public void setVictimAddressState(State victimAddressState) {
        this.victimAddressState = victimAddressState;
    }

    public City getVictimAddressCity() {
        return victimAddressCity;
    }

    public void setVictimAddressCity(City victimAddressCity) {
        this.victimAddressCity = victimAddressCity;
    }

    public String getVictimAddressStreet() {
        return victimAddressStreet;
    }

    public void setVictimAddressStreet(String victimAddressStreet) {
        this.victimAddressStreet = victimAddressStreet;
    }

    public String getVictimSex() {
        return victimSex;
    }

    public void setVictimSex(String victimSex) {
        this.victimSex = victimSex;
    }

    public String getVictimSexOption() {
        return victimSexOption;
    }

    public void setVictimSexOption(String victimSexOption) {
        this.victimSexOption = victimSexOption;
    }

    public String getVictimAgeGroup() {
        return victimAgeGroup;
    }

    public void setVictimAgeGroup(String victimAgeGroup) {
        this.victimAgeGroup = victimAgeGroup;
    }

    public String getVictimEthnicity() {
        return victimEthnicity;
    }

    public void setVictimEthnicity(String victimEthnicity) {
        this.victimEthnicity = victimEthnicity;
    }

    public Integer getOffenderType() {
        return offenderType;
    }

    public void setOffenderType(Integer offenderType) {
        this.offenderType = offenderType;
    }

    public String getOffenderName() {
        return offenderName;
    }

    public void setOffenderName(String offenderName) {
        this.offenderName = offenderName;
    }

    public State getOffenderAddressState() {
        return offenderAddressState;
    }

    public void setOffenderAddressState(State offenderAddressState) {
        this.offenderAddressState = offenderAddressState;
    }

    public City getOffenderAddressCity() {
        return offenderAddressCity;
    }

    public void setOffenderAddressCity(City offenderAddressCity) {
        this.offenderAddressCity = offenderAddressCity;
    }

    public String getOffenderAddressStreet() {
        return offenderAddressStreet;
    }

    public void setOffenderAddressStreet(String offenderAddressStreet) {
        this.offenderAddressStreet = offenderAddressStreet;
    }

    public String getOffenderSex() {
        return offenderSex;
    }

    public void setOffenderSex(String offenderSex) {
        this.offenderSex = offenderSex;
    }

    public String getOffenderSexOption() {
        return offenderSexOption;
    }

    public void setOffenderSexOption(String offenderSexOption) {
        this.offenderSexOption = offenderSexOption;
    }

    public String getOffenderAgeGroup() {
        return offenderAgeGroup;
    }

    public void setOffenderAgeGroup(String offenderAgeGroup) {
        this.offenderAgeGroup = offenderAgeGroup;
    }

    public String getOffenderEthnicity() {
        return offenderEthnicity;
    }

    public void setOffenderEthnicity(String offenderEthnicity) {
        this.offenderEthnicity = offenderEthnicity;
    }
}
