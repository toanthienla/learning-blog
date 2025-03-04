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
public class MaterialProgress extends Material{
    private Date completeDate;

    public MaterialProgress() {
    }

    public MaterialProgress(Date completeDate) {
        this.completeDate = completeDate;
    }

    public MaterialProgress(Date completeDate, String location, int materialId, String materialName, String materialType, Date lastUpdate, int moduleId) {
        super(location, materialId, materialName, materialType, lastUpdate, moduleId);
        this.completeDate = completeDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }
    
    
}
