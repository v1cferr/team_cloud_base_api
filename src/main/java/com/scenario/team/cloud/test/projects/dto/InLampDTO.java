package com.scenario.team.cloud.test.projects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InLampDTO {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Room ID is required")
    private Integer roomId;
    
    public InLampDTO() {}
    
    public InLampDTO(String name, Integer roomId) {
        this.name = name;
        this.roomId = roomId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getRoomId() {
        return roomId;
    }
    
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
}
