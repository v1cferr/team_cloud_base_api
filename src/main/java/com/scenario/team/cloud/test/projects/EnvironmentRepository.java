package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Environment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnvironmentRepository extends JpaRepository<Environment, Integer> {
    
    @Query("SELECT e FROM Environment e WHERE e.project.id = :projectId")
    List<Environment> findByProjectId(@Param("projectId") Integer projectId);
    
    @Query("SELECT e FROM Environment e LEFT JOIN FETCH e.lights WHERE e.id = :id")
    Environment findByIdWithLights(@Param("id") Integer id);
    
    @Query("SELECT e FROM Environment e LEFT JOIN FETCH e.lights WHERE e.project.id = :projectId")
    List<Environment> findByProjectIdWithLights(@Param("projectId") Integer projectId);
}
