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
public class Question {

    private int id;
    private String title;
    private final ArrayList<Option> options = new ArrayList<>();
    private int point;

    public Question() {
        this.id = -1;
        this.title = "";
        this.point = 0;
    }

    public Question(int id, String title, int point) {
        this.id = id;
        this.title = title;
        this.point = point;
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

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void addOption(Option option) {
        this.options.add(option);
    }

    public void removeOption(Option option) {
        this.options.remove(option);
    }

    public void removeOption(char id) {
        if (Character.isLowerCase(id)) {
            this.options.removeIf(op -> (op.getId() == (char) (id - 32)));
        } else {
            this.options.removeIf(op -> (op.getId() == id));
        }
    }

    public Option getCorrectOption() {
        for (Option option : options) {
            if (option.isCorrect()) {
                return option;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%d. %s\n", id, title));
        for (Option option : options) {
            sb.append(option);
            sb.append("\n");
        }

        sb.append("------\n");

        return sb.toString();
    }
}
