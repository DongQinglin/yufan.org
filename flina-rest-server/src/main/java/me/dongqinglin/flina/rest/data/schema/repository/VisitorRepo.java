package me.dongqinglin.flina.rest.data.schema.repository;

import me.dongqinglin.flina.rest.data.schema.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VisitorRepo extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findOneByName(String name);
    Optional<Visitor> findOneByEmail(String email);
}
