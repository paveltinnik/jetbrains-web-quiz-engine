package com.paveltinnik.webquizengine.engine.entities;

public class Answer {
    private boolean success;
    private String feedback;

    public Answer() {}

    public Answer(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}