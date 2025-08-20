package com.scenario.team.cloud.test.websocket;

import com.scenario.team.cloud.test.projects.LightRepository;
import com.scenario.team.cloud.test.projects.domain.Light;
import com.scenario.team.cloud.test.projects.dto.OutLightDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LightStatusService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LightRepository lightRepository;

    private final Random random = new Random();

    @Scheduled(fixedRate = 10000) // Execute every 10 seconds
    @Transactional
    public void updateRandomLightStatuses() {
        List<Light> allLights = lightRepository.findAll();
        
        if (allLights.isEmpty()) {
            return;
        }

        // Randomly update status of some lights
        allLights.forEach(light -> {
            // 30% chance to toggle the light status
            if (random.nextDouble() < 0.3) {
                light.setStatus(!light.getStatus());
                lightRepository.save(light);
            }
        });

        // Send updated light statuses via WebSocket
        List<OutLightDTO> lightStatuses = allLights.stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());

        messagingTemplate.convertAndSend("/topic/lights", lightStatuses);
    }

    public void sendLightUpdate(OutLightDTO lightDTO) {
        messagingTemplate.convertAndSend("/topic/lights/update", lightDTO);
    }

    public void sendLightStatusUpdate(Integer lightId, Boolean status) {
        LightStatusUpdate update = new LightStatusUpdate(lightId, status);
        messagingTemplate.convertAndSend("/topic/lights/status", update);
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

    public static class LightStatusUpdate {
        private Integer lightId;
        private Boolean status;

        public LightStatusUpdate(Integer lightId, Boolean status) {
            this.lightId = lightId;
            this.status = status;
        }

        public Integer getLightId() {
            return lightId;
        }

        public void setLightId(Integer lightId) {
            this.lightId = lightId;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
    }
}
