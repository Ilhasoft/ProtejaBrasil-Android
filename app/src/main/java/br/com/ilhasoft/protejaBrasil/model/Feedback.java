package br.com.ilhasoft.protejaBrasil.model;

/**
 * Created by john-mac on 1/8/16.
 */
public class Feedback {

    public enum FeedbackType {
        doubt,
        suggestion,
        criticism,
        compliment
    }

    private FeedbackType type;

    private String name;

    private String email;

    private String message;

    public Feedback(FeedbackType type, String name, String email, String message) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public FeedbackType getType() {
        return type;
    }

    public void setType(FeedbackType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
