package model;

import java.sql.Date;

public class Course {
    private int courseId;
    private String courseName;
    private Date publicDate;
    private Date lastUpdate;
    private int authorId;

    public Course(int courseId, String courseName, Date publicDate, Date lastUpdate, int authorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.publicDate = publicDate;
        this.lastUpdate = lastUpdate;
        this.authorId = authorId;
    }

    public Course() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}