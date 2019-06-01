package board.board.repository;

import board.board.model.SprintBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintBacklogRepository extends JpaRepository<SprintBacklog, Long> {

    @Query("SELECT spback FROM SprintBacklog spback where spback.sprintid = :sprintid")
    List<SprintBacklog> findBySprintid(@Param("sprintid") Long sprintid);
}
