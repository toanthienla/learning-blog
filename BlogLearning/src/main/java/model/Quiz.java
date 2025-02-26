/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Asus
 */
public class Quiz {

    private int id;
    private String title;
    private final ArrayList<Question> questions = new ArrayList<>();

    public Quiz() {
        this.id = -1;
        this.title = "";
    }

    public Quiz(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }

    public void removeQuestion(int index) {
        this.questions.remove(index);
    }

    public int getTotalScore(ArrayList<Character> answer) {
        int index = 0, total = 0;

        for (Question question : questions) {
            if (question.getCorrectOption().getId() == answer.get(index)) {
                total += question.getPoint();
            }
        }

        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%d. %s\n", id, title));
        for (Question question : questions) {
            sb.append(question);
        }

        return sb.toString();
    }
}
