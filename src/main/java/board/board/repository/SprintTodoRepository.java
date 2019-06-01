package board.board.repository;

import board.board.model.SprintTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintTodoRepository extends JpaRepository<SprintTodo, Long> {

    @Query("SELECT sptodo FROM SprintTodo sptodo where sptodo.sprintid = :sprintid")
    List<SprintTodo> findBySprintid(@Param("sprintid") Long sprintid);
}
