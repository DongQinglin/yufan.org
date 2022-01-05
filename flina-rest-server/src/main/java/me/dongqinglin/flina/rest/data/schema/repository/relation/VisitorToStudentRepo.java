package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToStudentWithStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorToStudentRepo extends JpaRepository<VisitorToStudentWithStatus, Long> {

    Optional<VisitorToStudentWithStatus> findOneByVisitor_name(String username);

    Optional<VisitorToStudentWithStatus> findOneByVisitor_id(Long id);

    List<VisitorToStudentWithStatus> findAllByVisitor_idIn(Iterable<Long> ids);

    Optional<VisitorToStudentWithStatus> findOneByVisitor_email(String email);

}
