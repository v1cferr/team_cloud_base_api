package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Project;
import com.scenario.team.cloud.test.projects.exceptions.ProjectNotFoundException;
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

    public void updateProject(int id, @Nonnull Project project) {
        var currentProject = this.repository.findById(id).orElseThrow(ProjectNotFoundException::new);
        currentProject.setName(project.getName());

        // Antes de salvar, preciso validar se o novo nome é valido
        if (currentProject.getName() == null || currentProject.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do projeto inválido");
        }

        this.repository.save(currentProject);
    }

    public void deleteProject(int id) {
        this.repository.deleteById(id);
    }

    @Nonnull
    public List<Project> listAllProjects() {
        return this.repository.findAll().stream().sorted((p1, p2) -> {
            var id1 = p1.getId();
            var id2 = p2.getId();
            return (id1 != null ? id1 : 0) - (id2 != null ? id2 : 0);
        }).toList();
    }
}