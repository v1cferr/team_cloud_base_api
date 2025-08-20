package com.scenario.team.cloud.test.projects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InLightDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    private String description;
    private String location;
    
    @NotNull(message = "Environment ID is required")
    private Integer environmentId;
    
    public InLightDTO() {}
    
    public InLightDTO(String name, String description, String location, Integer environmentId) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.environmentId = environmentId;
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
}
