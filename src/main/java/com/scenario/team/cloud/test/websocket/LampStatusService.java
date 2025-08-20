package com.scenario.team.cloud.test.websocket;

import com.scenario.team.cloud.test.projects.LampRepository;
import com.scenario.team.cloud.test.projects.domain.Lamp;
import com.scenario.team.cloud.test.projects.dto.OutLampDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LampStatusService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private LampRepository lampRepository;

    private final Random random = new Random();

    @Scheduled(fixedRate = 10000) // Execute every 10 seconds
    @Transactional
    public void updateRandomLampStatuses() {
        List<Lamp> allLamps = lampRepository.findAll();
        
        if (allLamps.isEmpty()) {
            return;
        }

        // Randomly update status of some lamps
        allLamps.forEach(lamp -> {
            // 30% chance to toggle the lamp status
            if (random.nextDouble() < 0.3) {
                lamp.setStatus(!lamp.getStatus());
                lampRepository.save(lamp);
            }
        });

        // Send updated lamp statuses via WebSocket
        List<OutLampDTO> lampStatuses = allLamps.stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());

        messagingTemplate.convertAndSend("/topic/lamps", lampStatuses);
    }

    public void sendLampUpdate(OutLampDTO lampDTO) {
        messagingTemplate.convertAndSend("/topic/lamps/update", lampDTO);
    }

    public void sendLampStatusUpdate(Integer lampId, Boolean status) {
        LampStatusUpdate update = new LampStatusUpdate(lampId, status);
        messagingTemplate.convertAndSend("/topic/lamps/status", update);
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

    public static class LampStatusUpdate {
        private Integer lampId;
        private Boolean status;

        public LampStatusUpdate(Integer lampId, Boolean status) {
            this.lampId = lampId;
            this.status = status;
        }

        public Integer getLampId() {
            return lampId;
        }

        public void setLampId(Integer lampId) {
            this.lampId = lampId;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
    }
}
