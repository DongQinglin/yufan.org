package me.dongqinglin.flina.rest.data.schema.repository.relation;

import me.dongqinglin.flina.rest.data.schema.entity.relation.VisitorToUserWithStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorToUserRepo extends JpaRepository<VisitorToUserWithStatus, Long> {
    Optional<VisitorToUserWithStatus> findOneByVisitor_name(String username);

    Optional<VisitorToUserWithStatus> findOneByVisitor_id(Long id);

    List<VisitorToUserWithStatus> findAllByVisitor_idIn(Iterable<Long> ids);

    Optional<VisitorToUserWithStatus> findOneByVisitor_email(String email);
}
