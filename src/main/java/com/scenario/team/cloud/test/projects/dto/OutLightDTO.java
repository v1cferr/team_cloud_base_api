package com.scenario.team.cloud.test.projects.dto;

public class OutLightDTO {
    
    private Integer id;
    private String name;
    private String description;
    private Boolean status;
    private String location;
    private Integer environmentId;
    private String environmentName;
    
    public OutLightDTO() {}
    
    public OutLightDTO(Integer id, String name, String description, Boolean status, String location, Integer environmentId, String environmentName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.location = location;
        this.environmentId = environmentId;
        this.environmentName = environmentName;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getStatus() {
        return status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Integer getEnvironmentId() {
        return environmentId;
    }
    
    public void setEnvironmentId(Integer environmentId) {
        this.environmentId = environmentId;
    }
    
    public String getEnvironmentName() {
        return environmentName;
    }
    
    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }
}
