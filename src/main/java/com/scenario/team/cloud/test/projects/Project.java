package com.scenario.team.cloud.test.projects;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table
public class Project {
    @Id
    @SequenceGenerator(name = "project_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_sequence")
    @Nullable
    private Integer id;

    @Nullable
    private String name;

    public Project(@Nullable String name) {
        this.name = name;
    }

    public Project() {
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }
}
