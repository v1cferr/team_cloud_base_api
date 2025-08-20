package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Light;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LightRepository extends JpaRepository<Light, Integer> {
    
    @Query("SELECT l FROM Light l WHERE l.environment.id = :environmentId")
    List<Light> findByEnvironmentId(@Param("environmentId") Integer environmentId);
    
    @Query("SELECT l FROM Light l WHERE l.environment.project.id = :projectId")
    List<Light> findByProjectId(@Param("projectId") Integer projectId);
    
    @Modifying
    @Query("UPDATE Light l SET l.status = :status WHERE l.id = :id")
    void updateLightStatus(@Param("id") Integer id, @Param("status") Boolean status);
    
    @Query("SELECT l FROM Light l")
    List<Light> findAllLights();
}
