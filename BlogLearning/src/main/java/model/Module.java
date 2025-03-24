package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Module {

    private int moduleId;
    private String moduleName;
    private Date lastUpdate; // java.sql.Date
    private int courseId;
    private List<MaterialProgress> materials;

    public Module() {
        this.materials = new ArrayList<>();
    }

    public Module(int moduleId, String moduleName, Date lastUpdate, int courseId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.lastUpdate = lastUpdate;
        this.courseId = courseId;
        this.materials = new ArrayList<>();
    }

    public Module(int moduleId, String moduleName, java.util.Date lastUpdate, int courseId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.lastUpdate = lastUpdate != null ? new Date(lastUpdate.getTime()) : null; // Chuyển java.util.Date thành java.sql.Date
        this.courseId = courseId;
        this.materials = new ArrayList<>();
    }

    public Module(int moduleId, String moduleName, int courseId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.courseId = courseId;
        this.materials = new ArrayList<>();
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdate(java.util.Date lastUpdate) {
        this.lastUpdate = lastUpdate != null ? new Date(lastUpdate.getTime()) : null;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<MaterialProgress> getMaterials() {
        return materials;
    }

    public MaterialProgress getMaterial(int materialId) {
        for (MaterialProgress material : materials) {
            if (material.getMaterialId() == materialId) {
                return material;
            }
        }
        return null;
    }

    public void addMaterial(MaterialProgress material) {
        materials.add(material);
    }

    @Override
    public String toString() {
        return "Module{" +
                "moduleId=" + moduleId +
                ", moduleName='" + moduleName + '\'' +
                ", lastUpdate=" + lastUpdate +
                ", courseId=" + courseId +
                ", materials=" + materials +
                '}';
    }
}