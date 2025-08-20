package com.scenario.team.cloud.test.projects.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "light")
public class Light {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "light_id_sequence")
    @SequenceGenerator(name = "light_id_sequence", sequenceName = "light_id_sequence", allocationSize = 1)
    private Integer id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private Boolean status = false; // false = OFF, true = ON
    
    private String location; // Localização dentro do ambiente
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id", nullable = false)
    private Environment environment;
    
    // Constructors
    public Light() {}
    
    public Light(String name, String description, String location, Environment environment) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.environment = environment;
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
    
    public Environment getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
