/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author hoqua
 */
public class CourseProgress extends Course{
    private double progress;

    public CourseProgress() {
    }

    public CourseProgress(double progress, int courseId, String courseName, Date publicDate, Date lastUpdate, int authorId) {
        super(courseId, courseName, publicDate, lastUpdate, authorId);
        this.progress = progress;
    }

    public double getProgress() {
        return progress;
    }
}
