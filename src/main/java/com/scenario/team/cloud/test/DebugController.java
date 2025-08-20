package com.scenario.team.cloud.test;

import com.scenario.team.cloud.test.projects.ProjectService;
import com.scenario.team.cloud.test.projects.RoomService;
import com.scenario.team.cloud.test.projects.LampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @Autowired
    private DataSource dataSource;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private RoomService roomService;
    
    @Autowired
    private LampService lampService;

    @GetMapping("/database-status")
    public Map<String, Object> getDatabaseStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // Teste de conexão
            try (Connection conn = dataSource.getConnection()) {
                status.put("database_connected", true);
                status.put("database_url", conn.getMetaData().getURL());
                status.put("database_user", conn.getMetaData().getUserName());
            }
            
            // Contagem de registros
            status.put("projects_count", projectService.listAllProjects().size());
            status.put("rooms_count", roomService.getAllRooms().size());
            status.put("lamps_count", lampService.getAllLamps().size());
            
            // Lista de projetos
            status.put("projects", projectService.listAllProjects());
            status.put("rooms", roomService.getAllRooms());
            status.put("lamps", lampService.getAllLamps());
            
        } catch (Exception e) {
            status.put("database_connected", false);
            status.put("error", e.getMessage());
        }
        
        return status;
    }
    
    @GetMapping("/recreate-data")
    public Map<String, String> recreateData() {
        Map<String, String> result = new HashMap<>();
        try {
            // Forçar execução do DataInitializer
            DataInitializer initializer = new DataInitializer();
            // Note: Isso é só para debug, normalmente não faria isso
            result.put("status", "Data recreation attempted - check /debug/database-status");
        } catch (Exception e) {
            result.put("status", "Error: " + e.getMessage());
        }
        return result;
    }
    
    @GetMapping("/clear-all-data")
    public Map<String, String> clearAllData() {
        Map<String, String> result = new HashMap<>();
        try {
            // Usar SQL nativo para limpar tudo
            javax.sql.DataSource dataSource = this.dataSource;
            try (java.sql.Connection conn = dataSource.getConnection();
                 java.sql.Statement stmt = conn.createStatement()) {
                
                // Deletar dados em ordem (respeitando foreign keys)
                stmt.executeUpdate("DELETE FROM lamp");
                stmt.executeUpdate("DELETE FROM light"); 
                stmt.executeUpdate("DELETE FROM room");
                stmt.executeUpdate("DELETE FROM environment");
                stmt.executeUpdate("DELETE FROM project");
                
                // Reset sequences
                stmt.executeUpdate("ALTER SEQUENCE project_id_sequence RESTART WITH 1");
                stmt.executeUpdate("ALTER SEQUENCE room_id_sequence RESTART WITH 1");  
                stmt.executeUpdate("ALTER SEQUENCE lamp_id_sequence RESTART WITH 1");
            }
            
            result.put("status", "✅ Todos os dados foram limpos com sucesso!");
            result.put("projects_remaining", String.valueOf(projectService.listAllProjects().size()));
            result.put("rooms_remaining", String.valueOf(roomService.getAllRooms().size()));
            result.put("lamps_remaining", String.valueOf(lampService.getAllLamps().size()));
            
        } catch (Exception e) {
            result.put("status", "❌ Erro ao limpar dados: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
