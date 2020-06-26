package br.com.ilhasoft.protejaBrasil.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by johncordeiro on 21/10/15.
 */
public class InternetCrime extends Report {

    @SerializedName("violation_type")
    private InternetViolation violationType;

    @SerializedName("url_website")
    private String website;

    private String description;

    public InternetViolation getViolationType() {
        return violationType;
    }

    public void setViolationType(InternetViolation violationType) {
        this.violationType = violationType;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
