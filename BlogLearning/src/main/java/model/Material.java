package model;

import java.sql.Date;

public class Material {

    private String location;
    private int materialId;
    private String materialName;
    private String materialType;
    private Date lastUpdate;
    private int moduleId;

    public Material() {
    }

    public Material(String location, int materialId, String materialName, String materialType, Date lastUpdate, int moduleId) {
        this.location = location;
        this.materialId = materialId;
        this.materialName = materialName;
        this.materialType = materialType;
        this.lastUpdate = lastUpdate;
        this.moduleId = moduleId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public String toString() {
        return "Material{"
                + "location=" + location
                + ", materialId=" + materialId
                + ", materialName=" + materialName + '\''
                + ", materialType=" + materialType
                + ", lastUpdate=" + lastUpdate
                + ", moduleId=" + moduleId
                + '}';
    }

}
