package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.dto.InRoomDTO;
import com.scenario.team.cloud.test.projects.dto.OutRoomDTO;
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
@RequestMapping("/rooms")
@Tag(name = "Room Management", description = "APIs for managing project rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    @Operation(summary = "Get all rooms", description = "Retrieve all rooms in the system")
    public ResponseEntity<List<OutRoomDTO>> getAllRooms() {
        List<OutRoomDTO> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/project/{projectId}")
    @Operation(summary = "Get rooms by project", description = "Retrieve all rooms for a specific project")
    public ResponseEntity<List<OutRoomDTO>> getRoomsByProjectId(
            @Parameter(description = "Project ID") @PathVariable Integer projectId) {
        List<OutRoomDTO> rooms = roomService.getRoomsByProjectId(projectId);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID", description = "Retrieve a specific room with its lamps")
    public ResponseEntity<OutRoomDTO> getRoomById(
            @Parameter(description = "Room ID") @PathVariable Integer id) {
        return roomService.getRoomById(id)
                .map(room -> ResponseEntity.ok(room))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new room", description = "Create a new room for a project")
    public ResponseEntity<OutRoomDTO> createRoom(@Valid @RequestBody InRoomDTO roomDTO) {
        OutRoomDTO createdRoom = roomService.createRoom(roomDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update room", description = "Update an existing room")
    public ResponseEntity<OutRoomDTO> updateRoom(
            @Parameter(description = "Room ID") @PathVariable Integer id,
            @Valid @RequestBody InRoomDTO roomDTO) {
        OutRoomDTO updatedRoom = roomService.updateRoom(id, roomDTO);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete room", description = "Delete a room and all its lamps")
    public ResponseEntity<Void> deleteRoom(
            @Parameter(description = "Room ID") @PathVariable Integer id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
