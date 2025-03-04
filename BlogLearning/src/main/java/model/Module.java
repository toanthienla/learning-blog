/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Module {
    private int moduleId;
    private String moduleName;
    private Date lastUpdate;
    private int courseId;
    private List<MaterialProgress> materials;
    
    public Module() {
    }

    public Module(int moduleId, String moduleName, Date lastUpdate, int courseId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.lastUpdate = lastUpdate;
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public List<MaterialProgress> getMaterials() {
        return materials;
    }

    public MaterialProgress getMaterial(int materialId){
        for (MaterialProgress material : materials) {
            if(material.getMaterialId() == materialId){
                return material;
            }
        }
        return null;
    }
    
    public void addMaterial(MaterialProgress material) {
        materials.add(material);
    }
    
}
