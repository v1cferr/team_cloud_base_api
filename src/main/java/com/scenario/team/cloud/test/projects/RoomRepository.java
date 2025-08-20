package com.scenario.team.cloud.test.projects;

import com.scenario.team.cloud.test.projects.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    
    @Query("SELECT r FROM Room r WHERE r.project.id = :projectId")
    List<Room> findByProjectId(@Param("projectId") Integer projectId);
    
    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.lamps WHERE r.id = :id")
    Room findByIdWithLamps(@Param("id") Integer id);
    
    @Query("SELECT r FROM Room r LEFT JOIN FETCH r.lamps WHERE r.project.id = :projectId")
    List<Room> findByProjectIdWithLamps(@Param("projectId") Integer projectId);
}
