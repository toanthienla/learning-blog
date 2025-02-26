/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Asus
 */
public class Option {

    private char id;
    private String content;
    private boolean isCorrect;
    private String feedback;

    public Option() {
        this.id = '\0';
        this.content = "";
        this.isCorrect = false;
        this.feedback = "";
    }

    public Option(char id, String content, boolean isCorrect, String feedback) {
        this.id = id;
        this.content = content;
        this.isCorrect = isCorrect;
        this.feedback = feedback;
    }

    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return String.format("%c. %s. (correct: %s)\nFeedback: %s", id, content, isCorrect, feedback);
    }
}
