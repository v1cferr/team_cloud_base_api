package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.dto.InEnvironmentDTO;
import com.scenario.team.cloud.test.projects.dto.OutEnvironmentDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/environments")
@Tag(name = "Environment Management", description = "APIs for managing project environments")
public class EnvironmentController {

    @Autowired
    private EnvironmentService environmentService;

    @GetMapping
    @Operation(summary = "Get all environments", description = "Retrieve all environments in the system")
    public ResponseEntity<List<OutEnvironmentDTO>> getAllEnvironments() {
        List<OutEnvironmentDTO> environments = environmentService.getAllEnvironments();
        return ResponseEntity.ok(environments);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get environments by project", description = "Retrieve all environments for a specific project")
    public ResponseEntity<List<OutEnvironmentDTO>> getEnvironmentsByProjectId(
            @Parameter(description = "Project ID") @PathVariable Integer projectId) {
        List<OutEnvironmentDTO> environments = environmentService.getEnvironmentsByProjectId(projectId);
        return ResponseEntity.ok(environments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get environment by ID", description = "Retrieve a specific environment with its lights")
    public ResponseEntity<OutEnvironmentDTO> getEnvironmentById(
            @Parameter(description = "Environment ID") @PathVariable Integer id) {
        return environmentService.getEnvironmentById(id)
                .map(environment -> ResponseEntity.ok(environment))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new environment", description = "Create a new environment for a project")
    public ResponseEntity<OutEnvironmentDTO> createEnvironment(@Valid @RequestBody InEnvironmentDTO environmentDTO) {
        OutEnvironmentDTO createdEnvironment = environmentService.createEnvironment(environmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEnvironment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update environment", description = "Update an existing environment")
    public ResponseEntity<OutEnvironmentDTO> updateEnvironment(
            @Parameter(description = "Environment ID") @PathVariable Integer id,
            @Valid @RequestBody InEnvironmentDTO environmentDTO) {
        OutEnvironmentDTO updatedEnvironment = environmentService.updateEnvironment(id, environmentDTO);
        return ResponseEntity.ok(updatedEnvironment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete environment", description = "Delete an environment and all its lights")
    public ResponseEntity<Void> deleteEnvironment(
            @Parameter(description = "Environment ID") @PathVariable Integer id) {
        environmentService.deleteEnvironment(id);
        return ResponseEntity.noContent().build();
    }
}
