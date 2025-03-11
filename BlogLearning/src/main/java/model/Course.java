package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Course {

    private int courseId;
    private String courseName;
    private Date publicDate;
    private Date lastUpdate;
    private int authorId;   
    private List<Module> modules;

    public Course(int courseId, String courseName, Date publicDate, Date lastUpdate, int authorId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.publicDate = publicDate;
        this.lastUpdate = lastUpdate;
        this.authorId = authorId;
        this.modules = new ArrayList();
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

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(int moduleId) {
        for (Module module : modules) {
            if (module.getModuleId() == moduleId) {
                return module;
            }
        }
        return null;
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    // Override toString để debug dễ hơn
    @Override
    public String toString() {
        return "Course{"
                + "courseId=" + courseId
                + ", courseName='" + courseName + '\''
                + ", publicDate=" + publicDate
                + ", lastUpdate=" + lastUpdate
                + ", authorId=" + authorId
                + '}';
    }

}
