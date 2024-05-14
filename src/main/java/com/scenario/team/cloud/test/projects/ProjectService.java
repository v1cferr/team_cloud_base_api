package com.scenario.team.cloud.test.projects;

import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Nonnull
    private final ProjectRepository repository;

    public ProjectService(@Nonnull ProjectRepository repository) {
        this.repository = repository;
    }

    public void insertProject(@Nonnull Project project) {
        this.repository.save(project);
    }

    @Nonnull
    public List<Project> listAllProjects() {
        return this.repository.findAll();
    }
}