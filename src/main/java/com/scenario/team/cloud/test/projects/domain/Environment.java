package com.scenario.team.cloud.test.projects.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "environment")
public class Environment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "environment_id_sequence")
    @SequenceGenerator(name = "environment_id_sequence", sequenceName = "environment_id_sequence", allocationSize = 1)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Light> lights = new ArrayList<>();
    
    // Constructors
    public Environment() {}
    
    public Environment(String name, String description, Project project) {
        this.name = name;
        this.description = description;
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Project getProject() {
        return project;
    }
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public List<Light> getLights() {
        return lights;
    }
    
    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
    
    public void addLight(Light light) {
        lights.add(light);
        light.setEnvironment(this);
    }
    
    public void removeLight(Light light) {
        lights.remove(light);
        light.setEnvironment(null);
    }
}
