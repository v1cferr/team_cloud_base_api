package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.dto.InLampDTO;
import com.scenario.team.cloud.test.projects.dto.OutLampDTO;
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
@RequestMapping("/lamps")
@Tag(name = "Lamp Management", description = "APIs for managing room lamps")
public class LampController {

    @Autowired
    private LampService lampService;

    @GetMapping
    @Operation(summary = "Get all lamps", description = "Retrieve all lamps in the system")
    public ResponseEntity<List<OutLampDTO>> getAllLamps() {
        List<OutLampDTO> lamps = lampService.getAllLamps();
        return ResponseEntity.ok(lamps);
    }

    @GetMapping("/room/{roomId}")
    @Operation(summary = "Get lamps by room", description = "Retrieve all lamps for a specific room")
    public ResponseEntity<List<OutLampDTO>> getLampsByRoomId(
            @Parameter(description = "Room ID") @PathVariable Integer roomId) {
        List<OutLampDTO> lamps = lampService.getLampsByRoomId(roomId);
        return ResponseEntity.ok(lamps);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get lamps by project", description = "Retrieve all lamps for a specific project")
    public ResponseEntity<List<OutLampDTO>> getLampsByProjectId(
            @Parameter(description = "Project ID") @PathVariable Integer projectId) {
        List<OutLampDTO> lamps = lampService.getLampsByProjectId(projectId);
        return ResponseEntity.ok(lamps);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lamp by ID", description = "Retrieve a specific lamp")
    public ResponseEntity<OutLampDTO> getLampById(
            @Parameter(description = "Lamp ID") @PathVariable Integer id) {
        return lampService.getLampById(id)
                .map(lamp -> ResponseEntity.ok(lamp))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new lamp", description = "Create a new lamp for a room")
    public ResponseEntity<OutLampDTO> createLamp(@Valid @RequestBody InLampDTO lampDTO) {
        OutLampDTO createdLamp = lampService.createLamp(lampDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLamp);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update lamp", description = "Update an existing lamp")
    public ResponseEntity<OutLampDTO> updateLamp(
            @Parameter(description = "Lamp ID") @PathVariable Integer id,
            @Valid @RequestBody InLampDTO lampDTO) {
        OutLampDTO updatedLamp = lampService.updateLamp(id, lampDTO);
        return ResponseEntity.ok(updatedLamp);
    }

    @PutMapping("/{id}/toggle")
    @Operation(summary = "Toggle lamp status", description = "Toggle lamp on/off status")
    public ResponseEntity<OutLampDTO> toggleLampStatus(
            @Parameter(description = "Lamp ID") @PathVariable Integer id) {
        OutLampDTO updatedLamp = lampService.toggleLampStatus(id);
        return ResponseEntity.ok(updatedLamp);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Set lamp status", description = "Set lamp status to ON or OFF")
    public ResponseEntity<OutLampDTO> setLampStatus(
            @Parameter(description = "Lamp ID") @PathVariable Integer id,
            @RequestBody Map<String, Boolean> statusRequest) {
        Boolean status = statusRequest.get("status");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }
        OutLampDTO updatedLamp = lampService.setLampStatus(id, status);
        return ResponseEntity.ok(updatedLamp);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete lamp", description = "Delete a lamp")
    public ResponseEntity<Void> deleteLamp(
            @Parameter(description = "Lamp ID") @PathVariable Integer id) {
        lampService.deleteLamp(id);
        return ResponseEntity.noContent().build();
    }
}
