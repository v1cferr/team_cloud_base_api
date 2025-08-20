package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Environment;
import com.scenario.team.cloud.test.projects.domain.Project;
import com.scenario.team.cloud.test.projects.dto.InEnvironmentDTO;
import com.scenario.team.cloud.test.projects.dto.OutEnvironmentDTO;
import com.scenario.team.cloud.test.projects.dto.OutLightDTO;
import com.scenario.team.cloud.test.projects.exceptions.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnvironmentService {

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LightRepository lightRepository;

    public List<OutEnvironmentDTO> getAllEnvironments() {
        return environmentRepository.findAll().stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public List<OutEnvironmentDTO> getEnvironmentsByProjectId(Integer projectId) {
        return environmentRepository.findByProjectId(projectId).stream()
                .map(this::convertToOutDTO)
                .collect(Collectors.toList());
    }

    public Optional<OutEnvironmentDTO> getEnvironmentById(Integer id) {
        return environmentRepository.findById(id)
                .map(this::convertToOutDTOWithLights);
    }

    @Transactional
    public OutEnvironmentDTO createEnvironment(InEnvironmentDTO inEnvironmentDTO) {
        Project project = projectRepository.findById(inEnvironmentDTO.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + inEnvironmentDTO.getProjectId()));

        Environment environment = new Environment();
        environment.setName(inEnvironmentDTO.getName());
        environment.setDescription(inEnvironmentDTO.getDescription());
        environment.setProject(project);

        Environment savedEnvironment = environmentRepository.save(environment);
        return convertToOutDTO(savedEnvironment);
    }

    @Transactional
    public OutEnvironmentDTO updateEnvironment(Integer id, InEnvironmentDTO inEnvironmentDTO) {
        Environment environment = environmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Environment not found with id: " + id));

        if (!environment.getProject().getId().equals(inEnvironmentDTO.getProjectId())) {
            Project newProject = projectRepository.findById(inEnvironmentDTO.getProjectId())
                    .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + inEnvironmentDTO.getProjectId()));
            environment.setProject(newProject);
        }

        environment.setName(inEnvironmentDTO.getName());
        environment.setDescription(inEnvironmentDTO.getDescription());

        Environment savedEnvironment = environmentRepository.save(environment);
        return convertToOutDTO(savedEnvironment);
    }

    @Transactional
    public void deleteEnvironment(Integer id) {
        if (!environmentRepository.existsById(id)) {
            throw new RuntimeException("Environment not found with id: " + id);
        }
        environmentRepository.deleteById(id);
    }

    private OutEnvironmentDTO convertToOutDTO(Environment environment) {
        OutEnvironmentDTO dto = new OutEnvironmentDTO();
        dto.setId(environment.getId());
        dto.setName(environment.getName());
        dto.setDescription(environment.getDescription());
        dto.setProjectId(environment.getProject().getId());
        dto.setProjectName(environment.getProject().getName());
        return dto;
    }

    private OutEnvironmentDTO convertToOutDTOWithLights(Environment environment) {
        OutEnvironmentDTO dto = convertToOutDTO(environment);
        
        List<OutLightDTO> lightDTOs = environment.getLights().stream()
                .map(light -> new OutLightDTO(
                        light.getId(),
                        light.getName(),
                        light.getDescription(),
                        light.getStatus(),
                        light.getLocation(),
                        light.getEnvironment().getId(),
                        light.getEnvironment().getName()
                ))
                .collect(Collectors.toList());
        
        dto.setLights(lightDTOs);
        return dto;
    }
}
