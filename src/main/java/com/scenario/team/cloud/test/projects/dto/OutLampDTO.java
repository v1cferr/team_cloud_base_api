package com.scenario.team.cloud.test.projects.dto;

public class OutLampDTO {
    
    private Integer id;
    private String name;
    private Boolean status;
    private Integer roomId;
    private String roomName;
    
    public OutLampDTO() {}
    
    public OutLampDTO(Integer id, String name, Boolean status, Integer roomId, String roomName) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.roomId = roomId;
        this.roomName = roomName;
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
    
    public Boolean getStatus() {
        return status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    public Integer getRoomId() {
        return roomId;
    }
    
    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomName() {
        return roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
