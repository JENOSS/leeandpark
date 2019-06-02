package board.board.repository;

import board.board.model.SprintBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SprintBacklogRepository extends JpaRepository<SprintBacklog, Long> {

    @Query("SELECT spback FROM SprintBacklog spback where spback.sprintid = :sprintid")
    List<SprintBacklog> findBySprintid(@Param("sprintid") Long sprintid);

    @Transactional
    @Modifying
    @Query("UPDATE SprintBacklog sb SET sb.isdoing ='Y' WHERE sb.backlogid =:backlogid")
    void updateToDoing(@Param("backlogid") Long backlogid);


}
