package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Lamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LampRepository extends JpaRepository<Lamp, Integer> {
    
    @Query("SELECT l FROM Lamp l WHERE l.room.id = :roomId")
    List<Lamp> findByRoomId(@Param("roomId") Integer roomId);
    
    @Query("SELECT l FROM Lamp l WHERE l.room.project.id = :projectId")
    List<Lamp> findByProjectId(@Param("projectId") Integer projectId);
    
    @Modifying
    @Query("UPDATE Lamp l SET l.status = :status WHERE l.id = :id")
    void updateLampStatus(@Param("id") Integer id, @Param("status") Boolean status);
}
