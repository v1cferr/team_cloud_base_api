package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Environment;
import com.scenario.team.cloud.test.projects.domain.Light;
import com.scenario.team.cloud.test.projects.dto.InLightDTO;
import com.scenario.team.cloud.test.projects.dto.OutLightDTO;
import com.scenario.team.cloud.test.websocket.LightStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LightService {

    @Autowired
    private LightRepository lightRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private LightStatusService lightStatusService;

    public List<OutLightDTO> getAllLights() {
        return lightRepository.findAll().stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public List<OutLightDTO> getLightsByEnvironmentId(Integer environmentId) {
        return lightRepository.findByEnvironmentId(environmentId).stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public List<OutLightDTO> getLightsByProjectId(Integer projectId) {
        return lightRepository.findByProjectId(projectId).stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public Optional<OutLightDTO> getLightById(Integer id) {
        return lightRepository.findById(id)
                .map(this::convertToOutDTO);
    }

    @Transactional
    public OutLightDTO createLight(InLightDTO inLightDTO) {
        Environment environment = environmentRepository.findById(inLightDTO.getEnvironmentId())
                .orElseThrow(() -> new RuntimeException("Environment not found with id: " + inLightDTO.getEnvironmentId()));

        Light light = new Light();
        light.setName(inLightDTO.getName());
        light.setDescription(inLightDTO.getDescription());
        light.setLocation(inLightDTO.getLocation());
        light.setEnvironment(environment);
        light.setStatus(false); // Default OFF

        Light savedLight = lightRepository.save(light);
        return convertToOutDTO(savedLight);
    }

    @Transactional
    public OutLightDTO updateLight(Integer id, InLightDTO inLightDTO) {
        Light light = lightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Light not found with id: " + id));

        if (!light.getEnvironment().getId().equals(inLightDTO.getEnvironmentId())) {
            Environment newEnvironment = environmentRepository.findById(inLightDTO.getEnvironmentId())
                    .orElseThrow(() -> new RuntimeException("Environment not found with id: " + inLightDTO.getEnvironmentId()));
            light.setEnvironment(newEnvironment);
        }

        light.setName(inLightDTO.getName());
        light.setDescription(inLightDTO.getDescription());
        light.setLocation(inLightDTO.getLocation());

        Light savedLight = lightRepository.save(light);
        return convertToOutDTO(savedLight);
    }

    @Transactional
    public OutLightDTO toggleLightStatus(Integer id) {
        Light light = lightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Light not found with id: " + id));

        light.setStatus(!light.getStatus());
        Light savedLight = lightRepository.save(light);
        
        OutLightDTO result = convertToOutDTO(savedLight);
        // Send WebSocket update
        lightStatusService.sendLightUpdate(result);
        
        return result;
    }

    @Transactional
    public OutLightDTO setLightStatus(Integer id, Boolean status) {
        Light light = lightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Light not found with id: " + id));

        light.setStatus(status);
        Light savedLight = lightRepository.save(light);
        
        OutLightDTO result = convertToOutDTO(savedLight);
        // Send WebSocket update
        lightStatusService.sendLightUpdate(result);
        
        return result;
    }

    @Transactional
    public void deleteLight(Integer id) {
        if (!lightRepository.existsById(id)) {
            throw new RuntimeException("Light not found with id: " + id);
        }
        lightRepository.deleteById(id);
    }

    private OutLightDTO convertToOutDTO(Light light) {
        return new OutLightDTO(
                light.getId(),
                light.getName(),
                light.getDescription(),
                light.getStatus(),
                light.getLocation(),
                light.getEnvironment().getId(),
                light.getEnvironment().getName()
        );
    }
}
