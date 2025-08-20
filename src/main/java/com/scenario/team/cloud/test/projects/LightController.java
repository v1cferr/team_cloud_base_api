package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.dto.InLightDTO;
import com.scenario.team.cloud.test.projects.dto.OutLightDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lights")
@Tag(name = "Light Management", description = "APIs for managing environment lights")
public class LightController {

    @Autowired
    private LightService lightService;

    @GetMapping
    @Operation(summary = "Get all lights", description = "Retrieve all lights in the system")
    public ResponseEntity<List<OutLightDTO>> getAllLights() {
        List<OutLightDTO> lights = lightService.getAllLights();
        return ResponseEntity.ok(lights);
    }

    @GetMapping("/environment/{environmentId}")
    @Operation(summary = "Get lights by environment", description = "Retrieve all lights for a specific environment")
    public ResponseEntity<List<OutLightDTO>> getLightsByEnvironmentId(
            @Parameter(description = "Environment ID") @PathVariable Integer environmentId) {
        List<OutLightDTO> lights = lightService.getLightsByEnvironmentId(environmentId);
        return ResponseEntity.ok(lights);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get lights by project", description = "Retrieve all lights for a specific project")
    public ResponseEntity<List<OutLightDTO>> getLightsByProjectId(
            @Parameter(description = "Project ID") @PathVariable Integer projectId) {
        List<OutLightDTO> lights = lightService.getLightsByProjectId(projectId);
        return ResponseEntity.ok(lights);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get light by ID", description = "Retrieve a specific light")
    public ResponseEntity<OutLightDTO> getLightById(
            @Parameter(description = "Light ID") @PathVariable Integer id) {
        return lightService.getLightById(id)
                .map(light -> ResponseEntity.ok(light))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new light", description = "Create a new light for an environment")
    public ResponseEntity<OutLightDTO> createLight(@Valid @RequestBody InLightDTO lightDTO) {
        OutLightDTO createdLight = lightService.createLight(lightDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLight);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update light", description = "Update an existing light")
    public ResponseEntity<OutLightDTO> updateLight(
            @Parameter(description = "Light ID") @PathVariable Integer id,
            @Valid @RequestBody InLightDTO lightDTO) {
        OutLightDTO updatedLight = lightService.updateLight(id, lightDTO);
        return ResponseEntity.ok(updatedLight);
    }

    @PutMapping("/{id}/toggle")
    @Operation(summary = "Toggle light status", description = "Toggle light on/off status")
    public ResponseEntity<OutLightDTO> toggleLightStatus(
            @Parameter(description = "Light ID") @PathVariable Integer id) {
        OutLightDTO updatedLight = lightService.toggleLightStatus(id);
        return ResponseEntity.ok(updatedLight);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Set light status", description = "Set light status to ON or OFF")
    public ResponseEntity<OutLightDTO> setLightStatus(
            @Parameter(description = "Light ID") @PathVariable Integer id,
            @RequestBody Map<String, Boolean> statusRequest) {
        Boolean status = statusRequest.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }
        OutLightDTO updatedLight = lightService.setLightStatus(id, status);
        return ResponseEntity.ok(updatedLight);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete light", description = "Delete a light")
    public ResponseEntity<Void> deleteLight(
            @Parameter(description = "Light ID") @PathVariable Integer id) {
        lightService.deleteLight(id);
        return ResponseEntity.noContent().build();
    }
}
