package br.com.ilhasoft.protejaBrasil.model;

/**
 * Created by ilhasoft on 01/03/16.
 */
public class AccessibilityPrediction {
    private String id;
    private String description;

    public AccessibilityPrediction(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
