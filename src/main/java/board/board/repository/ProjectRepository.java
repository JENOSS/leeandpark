package board.board.repository;

import board.board.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Integer> {

    @Query("SELECT p FROM Project p where p.projectidx = :projectidx")
    Project findByProjectidx(@Param("projectidx") int projectidx);
}
