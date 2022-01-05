package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.relation.WorksToVisitorWithStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorksToVisitorRepo extends JpaRepository<WorksToVisitorWithStatus, Long> {

    Optional<WorksToVisitorWithStatus> findOneByWorks_id(Long id);


    Optional<WorksToVisitorWithStatus> findOneByVisitor_name(String username);

    Optional<WorksToVisitorWithStatus> findOneByVisitor_id(Long id);

    List<WorksToVisitorWithStatus> findAllByVisitor_idIn(Iterable<Long> ids);

    Optional<WorksToVisitorWithStatus> findOneByVisitor_email(String email);
}
