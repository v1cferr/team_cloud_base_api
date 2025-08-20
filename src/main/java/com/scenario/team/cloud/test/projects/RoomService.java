package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Project;
import com.scenario.team.cloud.test.projects.domain.Room;
import com.scenario.team.cloud.test.projects.dto.InRoomDTO;
import com.scenario.team.cloud.test.projects.dto.OutLampDTO;
import com.scenario.team.cloud.test.projects.dto.OutRoomDTO;
import com.scenario.team.cloud.test.projects.exceptions.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public List<OutRoomDTO> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public List<OutRoomDTO> getRoomsByProjectId(Integer projectId) {
        return roomRepository.findByProjectId(projectId).stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public Optional<OutRoomDTO> getRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(this::convertToOutDTOWithLamps);
    }

    @Transactional
    public OutRoomDTO createRoom(InRoomDTO inRoomDTO) {
        Project project = projectRepository.findById(inRoomDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + inRoomDTO.getProjectId()));

        Room room = new Room();
        room.setName(inRoomDTO.getName());
        room.setProject(project);

        Room savedRoom = roomRepository.save(room);
        return convertToOutDTO(savedRoom);
    }

    @Transactional
    public OutRoomDTO updateRoom(Integer id, InRoomDTO inRoomDTO) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + id));

        if (!room.getProject().getId().equals(inRoomDTO.getProjectId())) {
            Project newProject = projectRepository.findById(inRoomDTO.getProjectId())
                    .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + inRoomDTO.getProjectId()));
            room.setProject(newProject);
        }

        room.setName(inRoomDTO.getName());

        Room savedRoom = roomRepository.save(room);
        return convertToOutDTO(savedRoom);
    }

    @Transactional
    public void deleteRoom(Integer id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found with id: " + id);
        }
        roomRepository.deleteById(id);
    }

    private OutRoomDTO convertToOutDTO(Room room) {
        OutRoomDTO dto = new OutRoomDTO();
        dto.setId(room.getId());
        dto.setName(room.getName());
        dto.setProjectId(room.getProject().getId());
        dto.setProjectName(room.getProject().getName());
        return dto;
    }

    private OutRoomDTO convertToOutDTOWithLamps(Room room) {
        OutRoomDTO dto = convertToOutDTO(room);
        
        List<OutLampDTO> lampDTOs = room.getLamps().stream()
                .map(lamp -> new OutLampDTO(
                        lamp.getId(),
                        lamp.getName(),
                        lamp.getStatus(),
                        lamp.getRoom().getId(),
                        lamp.getRoom().getName()
                ))
                .collect(Collectors.toList());
        
        dto.setLamps(lampDTOs);
        return dto;
    }
}
