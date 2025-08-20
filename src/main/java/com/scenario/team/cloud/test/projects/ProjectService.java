package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.exceptions.BadRequestException;

import com.scenario.team.cloud.test.projects.domain.Project;
import com.scenario.team.cloud.test.projects.exceptions.ProjectNotFoundException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    @Nonnull
    private final ProjectRepository repository;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired  
    private LampService lampService;

    public ProjectService(@Nonnull ProjectRepository repository) {
        this.repository = repository;
    }

    public void insertProject(@Nonnull Project project) {
        // Validação do projeto antes de inserir
        if (project.getName() == null || project.getName().trim().isEmpty()) {
            throw new BadRequestException("Nome do projeto não pode ser vazio.");
        }
        this.repository.save(project);
    }

    public void updateProject(int id, @Nonnull Project project) {
        var currentProject = this.repository.findById(id).orElseThrow(ProjectNotFoundException::new);
        currentProject.setName(project.getName());

        // Antes de salvar, preciso validar se o novo nome é valido
        if (currentProject.getName() == null || currentProject.getName().trim().isEmpty()) {
            throw new BadRequestException("Nome do projeto não pode ser vazio.");
        }
        this.repository.save(currentProject);
    }

    @Transactional
    public void deleteProject(int id) {
        // Verificar se o projeto existe
        var project = this.repository.findById(id).orElseThrow(() -> 
            new ProjectNotFoundException("Projeto com ID " + id + " não encontrado"));
        
        try {
            // Deletar lâmpadas primeiro (por causa das foreign keys)
            this.repository.deleteLampsByProjectId(id);
            
            // Deletar rooms em seguida
            this.repository.deleteRoomsByProjectId(id);
            
            // Finalmente deletar o projeto
            this.repository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar projeto: " + e.getMessage(), e);
        }
    }

    @Nonnull
    public List<Project> listAllProjects() {
        return this.repository.findAll().stream().sorted((p1, p2) -> {
            var id1 = p1.getId();
            var id2 = p2.getId();
            return (id1 != null ? id1 : 0) - (id2 != null ? id2 : 0);
        }).toList();
    }
}