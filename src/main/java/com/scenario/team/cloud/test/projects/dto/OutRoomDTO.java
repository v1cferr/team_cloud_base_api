package com.scenario.team.cloud.test.projects.dto;

import java.util.List;

public class OutRoomDTO {
    
    private Integer id;
    private String name;
    private Integer projectId;
    private String projectName;
    private List<OutLampDTO> lamps;
    
    public OutRoomDTO() {}
    
    public OutRoomDTO(Integer id, String name, Integer projectId, String projectName) {
        this.id = id;
        this.name = name;
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
    
    public List<OutLampDTO> getLamps() {
        return lamps;
    }
    
    public void setLamps(List<OutLampDTO> lamps) {
        this.lamps = lamps;
    }
}
