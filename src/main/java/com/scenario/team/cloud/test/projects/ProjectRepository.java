package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    
    @Query("SELECT p FROM Project p LEFT JOIN FETCH p.rooms r LEFT JOIN FETCH r.lamps WHERE p.id = :id")
    Optional<Project> findByIdWithRoomsAndLamps(@Param("id") Integer id);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Lamp l WHERE l.room.project.id = :projectId")
    void deleteLampsByProjectId(@Param("projectId") Integer projectId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Room r WHERE r.project.id = :projectId")
    void deleteRoomsByProjectId(@Param("projectId") Integer projectId);
}
