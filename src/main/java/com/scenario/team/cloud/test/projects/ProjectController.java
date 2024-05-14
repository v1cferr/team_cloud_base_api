package com.scenario.team.cloud.test.projects;

import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Nonnull
    private final ProjectService projectService;

    public ProjectController(@Nonnull ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public void insertProject(@Nonnull @RequestBody InProjectDTO inProjectDTO) {
        this.projectService.insertProject(new Project(inProjectDTO.name()));
    }

    @GetMapping
    public List<OutProjectDTO> listProjects() {
        return this.projectService.listAllProjects().stream().map(project -> new OutProjectDTO(project.getId(), project.getName())).toList();
    }

}
