package com.scenario.team.cloud.test.projects.dto;

import java.util.List;

public class OutEnvironmentDTO {
    
    private Integer id;
    private String name;
    private String description;
    private Integer projectId;
    private String projectName;
    private List<OutLightDTO> lights;
    
    public OutEnvironmentDTO() {}
    
    public OutEnvironmentDTO(Integer id, String name, String description, Integer projectId, String projectName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.projectName = projectName;
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
    
    public Integer getProjectId() {
        return projectId;
    }
    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public List<OutLightDTO> getLights() {
        return lights;
    }
    
    public void setLights(List<OutLightDTO> lights) {
        this.lights = lights;
    }
}
