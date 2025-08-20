package com.scenario.team.cloud.test.projects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InRoomDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Project ID is required")
    private Integer projectId;
    
    public InRoomDTO() {}
    
    public InRoomDTO(String name, Integer projectId) {
        this.name = name;
        this.projectId = projectId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
