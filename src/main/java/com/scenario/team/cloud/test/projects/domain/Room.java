package com.scenario.team.cloud.test.projects.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_sequence")
    @SequenceGenerator(name = "room_id_sequence", sequenceName = "room_id_sequence", allocationSize = 1)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lamp> lamps = new ArrayList<>();
    
    // Constructors
    public Room() {}
    
    public Room(String name, Project project) {
        this.name = name;
        this.project = project;
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
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public List<Lamp> getLamps() {
        return lamps;
    }
    
    public void setLamps(List<Lamp> lamps) {
        this.lamps = lamps;
    }
    
    public void addLamp(Lamp lamp) {
        lamps.add(lamp);
        lamp.setRoom(this);
    }
    
    public void removeLamp(Lamp lamp) {
        lamps.remove(lamp);
        lamp.setRoom(null);
    }
}
