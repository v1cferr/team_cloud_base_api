package com.scenario.team.cloud.test;

import com.scenario.team.cloud.test.projects.ProjectService;
import com.scenario.team.cloud.test.projects.RoomService;
import com.scenario.team.cloud.test.projects.LampService;
import com.scenario.team.cloud.test.projects.domain.Project;
import com.scenario.team.cloud.test.projects.domain.Room;
import com.scenario.team.cloud.test.projects.domain.Lamp;
import com.scenario.team.cloud.test.projects.dto.InProjectDTO;
import com.scenario.team.cloud.test.projects.dto.InRoomDTO;
import com.scenario.team.cloud.test.projects.dto.InLampDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private LampService lampService;

    @Override
    public void run(String... args) throws Exception {
        // Verificar se já existem dados
        if (projectService.listAllProjects().isEmpty()) {
            initializeData();
        }
    }

    private void initializeData() {
        try {
            // Criar Projeto 1
            Project project1 = new Project("Casa Inteligente");
            projectService.insertProject(project1);
            
            // Criar Projeto 2  
            Project project2 = new Project("Projeto Smart Home");
            projectService.insertProject(project2);
            
            // Buscar projetos criados para obter IDs
            var projects = projectService.listAllProjects();
            Project casa = projects.stream().filter(p -> p.getName().equals("Casa Inteligente")).findFirst().orElse(null);
            Project smart = projects.stream().filter(p -> p.getName().equals("Projeto Smart Home")).findFirst().orElse(null);
            
            if (casa != null) {
                // Criar rooms para Casa Inteligente
                var roomDTO1 = new InRoomDTO("Sala de Estar", casa.getId());
                var room1 = roomService.createRoom(roomDTO1);
                
                var roomDTO2 = new InRoomDTO("Quarto Principal", casa.getId());
                var room2 = roomService.createRoom(roomDTO2);
                
                // Criar lâmpadas para Sala de Estar
                var lampDTO1 = new InLampDTO("Lustre Central", room1.getId());
                lampService.createLamp(lampDTO1);
                
                var lampDTO2 = new InLampDTO("Abajur de Mesa", room1.getId());
                lampService.createLamp(lampDTO2);
                
                // Criar lâmpadas para Quarto Principal
                var lampDTO3 = new InLampDTO("Luz de Teto", room2.getId());
                lampService.createLamp(lampDTO3);
            }
            
            if (smart != null) {
                // Criar rooms para Projeto Smart Home
                var roomDTO3 = new InRoomDTO("Cozinha", smart.getId());
                var room3 = roomService.createRoom(roomDTO3);
                
                var roomDTO4 = new InRoomDTO("Banheiro", smart.getId());
                var room4 = roomService.createRoom(roomDTO4);
                
                // Criar lâmpadas para Cozinha
                var lampDTO4 = new InLampDTO("Luz da Bancada", room3.getId());
                lampService.createLamp(lampDTO4);
                
                var lampDTO5 = new InLampDTO("Luz Central", room3.getId());
                lampService.createLamp(lampDTO5);
                
                // Criar lâmpadas para Banheiro
                var lampDTO6 = new InLampDTO("Luz do Espelho", room4.getId());
                lampService.createLamp(lampDTO6);
            }
            
            System.out.println("✅ Dados iniciais criados com sucesso!");
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao criar dados iniciais: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
