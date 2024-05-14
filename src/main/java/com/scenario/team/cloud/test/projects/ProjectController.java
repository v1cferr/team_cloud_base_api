package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Project;
import com.scenario.team.cloud.test.projects.dto.InProjectDTO;
import com.scenario.team.cloud.test.projects.dto.OutProjectDTO;
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

    @PutMapping("/{id}")
    public void editProject(@PathVariable("id") int id, @Nonnull @RequestBody InProjectDTO inProjectDTO) {
        this.projectService.updateProject(id, new Project(inProjectDTO.name()));
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable("id") int id) {
        this.projectService.deleteProject(id);
    }

    @GetMapping
    public List<OutProjectDTO> listProjects() {
        return this.projectService.listAllProjects().stream().map(project -> new OutProjectDTO(project.getId(), project.getName())).toList();
    }

}
