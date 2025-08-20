package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Lamp;
import com.scenario.team.cloud.test.projects.domain.Room;
import com.scenario.team.cloud.test.projects.dto.InLampDTO;
import com.scenario.team.cloud.test.projects.dto.OutLampDTO;
import com.scenario.team.cloud.test.websocket.LampStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LampService {

    @Autowired
    private LampRepository lampRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private LampStatusService lampStatusService;

    public List<OutLampDTO> getAllLamps() {
        return lampRepository.findAll().stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public List<OutLampDTO> getLampsByRoomId(Integer roomId) {
        return lampRepository.findByRoomId(roomId).stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public List<OutLampDTO> getLampsByProjectId(Integer projectId) {
        return lampRepository.findByProjectId(projectId).stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public Optional<OutLampDTO> getLampById(Integer id) {
        return lampRepository.findById(id)
                .map(this::convertToOutDTO);
    }

    @Transactional
    public OutLampDTO createLamp(InLampDTO inLampDTO) {
        Room room = roomRepository.findById(inLampDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + inLampDTO.getRoomId()));

        Lamp lamp = new Lamp();
        lamp.setName(inLampDTO.getName());
        lamp.setRoom(room);
        lamp.setStatus(false); // Default OFF

        Lamp savedLamp = lampRepository.save(lamp);
        return convertToOutDTO(savedLamp);
    }

    @Transactional
    public OutLampDTO updateLamp(Integer id, InLampDTO inLampDTO) {
        Lamp lamp = lampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lamp not found with id: " + id));

        if (!lamp.getRoom().getId().equals(inLampDTO.getRoomId())) {
            Room newRoom = roomRepository.findById(inLampDTO.getRoomId())
                    .orElseThrow(() -> new RuntimeException("Room not found with id: " + inLampDTO.getRoomId()));
            lamp.setRoom(newRoom);
        }

        lamp.setName(inLampDTO.getName());

        Lamp savedLamp = lampRepository.save(lamp);
        return convertToOutDTO(savedLamp);
    }

    @Transactional
    public OutLampDTO toggleLampStatus(Integer id) {
        Lamp lamp = lampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lamp not found with id: " + id));

        lamp.setStatus(!lamp.getStatus());
        Lamp savedLamp = lampRepository.save(lamp);
        
        OutLampDTO result = convertToOutDTO(savedLamp);
        // Send WebSocket update
        lampStatusService.sendLampUpdate(result);
        
        return result;
    }

    @Transactional
    public OutLampDTO setLampStatus(Integer id, Boolean status) {
        Lamp lamp = lampRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lamp not found with id: " + id));

        lamp.setStatus(status);
        Lamp savedLamp = lampRepository.save(lamp);
        
        OutLampDTO result = convertToOutDTO(savedLamp);
        // Send WebSocket update
        lampStatusService.sendLampUpdate(result);
        
        return result;
    }

    @Transactional
    public void deleteLamp(Integer id) {
        if (!lampRepository.existsById(id)) {
            throw new RuntimeException("Lamp not found with id: " + id);
        }
        lampRepository.deleteById(id);
    }

    private OutLampDTO convertToOutDTO(Lamp lamp) {
        return new OutLampDTO(
                lamp.getId(),
                lamp.getName(),
                lamp.getStatus(),
                lamp.getRoom().getId(),
                lamp.getRoom().getName()
        );
    }
}
