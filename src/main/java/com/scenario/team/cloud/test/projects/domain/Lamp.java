package com.scenario.team.cloud.test.projects.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "lamp")
public class Lamp {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lamp_id_sequence")
    @SequenceGenerator(name = "lamp_id_sequence", sequenceName = "lamp_id_sequence", allocationSize = 1)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Boolean status = false; // false = OFF, true = ON
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;
    
    // Constructors
    public Lamp() {}
    
    public Lamp(String name, Room room) {
        this.name = name;
        this.room = room;
        this.status = false;
    }
    
    // Getters and Setters
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
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room room) {
        this.room = room;
    }
}
